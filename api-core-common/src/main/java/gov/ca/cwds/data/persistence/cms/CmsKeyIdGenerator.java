package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.resources.ResourceParamValidator;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Java port of gov.ca.cwds.rest.util.jni.KeyJNI and underlying shared library, cws_randgen.cpp.
 *
 * <p>
 * To generateXYZ an identifier, the current date/timestamp is rearranged as shown below, placing
 * less-significant time units into more-significant fields. This convolution provides better
 * "hashing" into cache and the database.
 *
 * <blockquote>
 *
 * <pre>
 *   !   ~8 bits     !   6 bits  !   6 bits  ! 5 bits  ! 5 bits  !4 bits !    8 bits     !
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *   !  hundredths   !  seconds  !  minutes  !  hours  !   day   !month-1!  year - 1900  !
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * bytes !       :       !       :       !       :       !       :       !       :       !
 * </pre>
 *
 * </blockquote>
 *
 * As shown above, some bit values would produce unrealistic dates/times. For example, month values
 * of 0000-1011 represent January-December, but the field has room for unrealistic months 1100-1111.
 * Similarly unrealistic values could be placed into other fields. The first and last fields are of
 * special interest.
 *
 * <blockquote>
 *
 * <pre>
 *   hundredths:  Although shown as an 8-bit field here, not all unrealistic values are usable,
 *                since the overall number must fit into seven base-62 characters. That limits
 *                the hundredths field to values from 0 to 204.
 *
 *   year:        This algorithm supports years from 1900 to 2155.
 * </pre>
 *
 * </blockquote>
 *
 * <p>
 * Note that CWS/CMS has made use of <strong>unrealistic</strong> values on some occasions. For
 * example, the project may need to generateXYZ many identifiers during a minimum outage window. In
 * order to make all of those generated identifiers correspond to the date/hour range of the outage,
 * project-generated identifiers may coerce unrealistic values for minutes, seconds, and hundredths.
 * This explains why some identifiers may translate to timestamps with 7 digits (rather than the
 * usual 6) after the final decimal point -- It may be showing more than 99 "hundredths"! This also
 * means that the longest formatted date/timestamp output is 27 characters (including punctuation),
 * although it will usually fit in 26.
 *
 * <p>
 * Once packed into the bit arrangement shown above, the number is converted to seven base-62
 * characters, using first the digits 0-9, then uppercase letters, then lowercase letters.
 *
 * <p>
 * The final 3 characters of the identifier indicate the staff person (or project process) which
 * created the row.
 *
 * <p>
 * For the User Interface, the identifier can also be converted into a formatted 19-digit decimal
 * number. In the 19-digit format, the first 13 decimal digits are a conversion of the first seven
 * base-62 characters, while the last 6 decimal digits are an independent conversion of the last 3
 * base-62 characters (ie, staff person ID) from the identifier. The 19 digits are formatted into
 * three groups of four digits, followed by a final group of seven digits, so the full string length
 * is 22 characters with punctuation.
 *
 * <p>
 * In this source file, the 3 formats are referred to as:
 *
 * <blockquote>
 *
 * <pre>
 *   Key:            10 base-62 characters (0-9, A-Z, a-z):               tttttttppp
 *   UI Identifier:  19 decimal digits (22 characters with punctuation):  tttt-tttt-tttt-tpppppp
 *   Timestamp:      26 characters in DB2 format:                         YYYY-MM-DD-hh.mm.ss.xx0000
 *         or rarely 27 (as explained above):                             YYYY-MM-DD-hh.mm.ss.xxx0000
 * where:
 *   All timestamp fields include leading zeros.
 *   t... represents convolved time
 *   p... represents the staff person
 *   YYYY represents the year
 *   MM   represents the month
 *   DD   represents the day of the month
 *   hh   represents the hour (00 to 23)
 *   mm   represents minutes
 *   ss   represents seconds
 *   x... represents hundredths of seconds
 * </pre>
 *
 * </blockquote>
 *
 * @author CWDS API Team
 */
@SuppressWarnings({"fb-contrib:MDM_THREAD_YIELD", "findbugs:UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD"})
public class CmsKeyIdGenerator {

  private static final int LEN_KEYSTAFFID = 3;
  private static final int LEN_KEYTIMESTAMP = 7;
  private static final int LEN_UIIDSTAFFID = 6; // for converting a key to a UI identifier
  private static final int LEN_UIIDTIMESTAMP = 13;
  private static final long nSHIFT_HSECOND = 1L << 34; // NOSONAR 34 bit shift (2 ^ 34)
  private static final long nSHIFT_SECOND = 1L << 28; // NOSONAR 28 bit shift (2 ^ 28)
  private static final long nSHIFT_MINUTE = 1L << 22; // NOSONAR 22 bit shift (2 ^ 22)
  private static final long nSHIFT_HOUR = 1L << 17; // NOSONAR 17 bit shift (2 ^ 17)
  private static final long nSHIFT_DAY = 1L << 12; // NOSONAR 12 bit shift (2 ^ 12)
  private static final long nSHIFT_MONTH = 1L << 8; // NOSONAR 8 bit shift (2 ^ 8)
  private static final long nSHIFT_YEAR = 1L << 0; // NOSONAR 0 bit shift (2 ^ 0)

  //@formatter:off
  private static final long[] POWER_BASE62 = {
    1L,
    (long) Math.pow(62, 1),
    (long) Math.pow(62, 2),
    (long) Math.pow(62, 3),
    (long) Math.pow(62, 4),
    (long) Math.pow(62, 5),
    (long) Math.pow(62, 6),
    (long) Math.pow(62, 7),
    (long) Math.pow(62, 8),
    (long) Math.pow(62, 9),
    (long) Math.pow(62, 10),
    (long) Math.pow(62, 11),
    (long) Math.pow(62, 12),
    (long) Math.pow(62, 13),
    (long) Math.pow(62, 14),
    (long) Math.pow(62, 15),
    (long) Math.pow(62, 16),
    (long) Math.pow(62, 17),
    (long) Math.pow(62, 18)};

  private static final char[] ALPHABET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
    'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
  //@formatter:on

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsKeyIdGenerator.class);
  private static final String DEFAULT_USER_ID = "0X5";
  private static final Map<String, String> lastKeys =
    new PassiveExpiringMap<>(20, TimeUnit.SECONDS, new ConcurrentHashMap<>(20107));

  /**
   * Static class only, do not instantiate.
   */
  private CmsKeyIdGenerator() {
    // Static class only, do not instantiate.
  }

  /**
   * Generate next CWS/CMS identifier with the given staff id and current datetime.
   *
   * <p>
   * <strong>Synchronizing on the class monitor does NOT scale</strong> Instead, implement the
   * Iterator interface. Block only on unique staff id; don't block other staff.Construct an object
   * for a given staff id and generate keys with {@code Iterator.next()}.
   *
   * @param staffId the staffId
   * @return the unique key from staffId
   */
  public static String getNextValue(String staffId) {
    return StaffGate.getStaffGate(StringUtils.isNotBlank(staffId) ? staffId : DEFAULT_USER_ID)
      .getNextValue();
  }

  /**
   * Format CMS timestamp String, the last 7 characters of the key.
   *
   * @param ts seed timestamp
   * @return CMS formatted timestamp
   */
  public static String createTimestampStr(final Date ts) {
    return ts == null ? createTimestampStr()
      : longToStrN(7, timestampToLong(getTimestampSeed(ts)), POWER_BASE62);
  }

  /**
   * Format the CMS timestamp String, the first 7 characters of the key.
   *
   * <p>
   * Code taken originally from the original C++ algorithm, designed for legacy fat client Visual
   * Basic application. In that world of dial-up modems, the inefficiency of waiting on hundredths
   * of a second for a single user was acceptable. Obviously, this approach makes little sense today
   * in the age of web servers and wireless internet connections.
   * </p>
   *
   * @return CMS formatted timestamp
   */
  protected static String createTimestampStr() {
    long nTimestamp = 0;
    long nPreviousTimestamp = 0; // previous value - used for UNIQUENESS!

    // NEXT: #145948067: make previous timestamp thread-safe.
    while (true) { // NOSONAR
      nTimestamp = timestampToLong(getTimestampSeed(null));

      // If the timestamp value is the same as before, stay in the loop.
      // Otherwise, break out since it is unique.
      if (nTimestamp == nPreviousTimestamp) { // NOSONAR
        Thread.yield(); // From original algorithm. Sadness.
        continue;
      } else {
        break;
      }
    }

    // Convert the timestamp number to a base-62 string.
    return longToStrN(7, nTimestamp, POWER_BASE62);
  }

  /**
   * @param cal preferred timestamp
   * @return the timestamp in double
   */
  public static long timestampToLong(final Calendar cal) {
    long ret = 0;

    ret += cal.get(Calendar.MILLISECOND) / 10 * nSHIFT_HSECOND;
    ret += cal.get(Calendar.SECOND) * nSHIFT_SECOND;
    ret += cal.get(Calendar.MINUTE) * nSHIFT_MINUTE;
    ret += cal.get(Calendar.HOUR_OF_DAY) * nSHIFT_HOUR;
    ret += cal.get(Calendar.DATE) * nSHIFT_DAY;
    ret += cal.get(Calendar.MONTH) * nSHIFT_MONTH;
    ret += (cal.get(Calendar.YEAR) - 1900) * nSHIFT_YEAR;

    return ret;
  }

  /**
   * @param longTimestamp date decoded from Base62 to double
   * @return date
   */
  public static long longToTimestamp(long longTimestamp) {
    final Calendar cal = Calendar.getInstance();

    final long mseconds = (longTimestamp / nSHIFT_HSECOND);
    cal.set(Calendar.MILLISECOND, (int) (mseconds * 10));
    longTimestamp -= mseconds * nSHIFT_HSECOND;

    final long seconds = (longTimestamp / nSHIFT_SECOND);
    cal.set(Calendar.SECOND, (int) seconds);
    longTimestamp -= seconds * nSHIFT_SECOND;

    final long min = (longTimestamp / nSHIFT_MINUTE);
    cal.set(Calendar.MINUTE, (int) min);
    longTimestamp -= min * nSHIFT_MINUTE;

    final long hours = (longTimestamp / nSHIFT_HOUR);
    cal.set(Calendar.HOUR_OF_DAY, (int) hours);
    longTimestamp -= hours * nSHIFT_HOUR;

    final long day = (longTimestamp / nSHIFT_DAY);
    cal.set(Calendar.DATE, (int) day);
    longTimestamp -= day * nSHIFT_DAY;

    final long month = (longTimestamp / nSHIFT_MONTH);

    cal.set(Calendar.MONTH, (int) month);
    longTimestamp -= month * nSHIFT_MONTH;

    final long year = ((longTimestamp) / nSHIFT_YEAR);
    cal.set(Calendar.YEAR, (int) year + 1900);

    return cal.getTimeInMillis();
  }

  /**
   * @param dstLen the string width
   * @param src source value
   * @param powers the power vector for the destination base
   * @return the double to string
   */
  public static String longToStrN(int dstLen, long src, final long[] powers) {
    int i;
    int p;
    long integral;
    final char[] dest = new char[20];

    // Determine the largest power of the number.
    for (p = 0; src >= powers[p]; p++) {
      // NOSONAR
    }

    // Left-pad the string with the destination string width.
    final int pad = dstLen - p;

    if (pad < 0) {
      throw new ServiceException("invalid pad value");
    } else {
      for (i = 0; i < pad; i++) {
        dest[i] = ALPHABET[0];
      }

      for (i = 0; i < p; i++) {
        integral = src / powers[p - i - 1];

        // Break down the number and convert the integer portion to a character.
        dest[i + pad] = ALPHABET[(int) integral];
        src = src % powers[p - i - 1]; // NOSONAR
      }
    }

    final StringBuilder buf = new StringBuilder();
    for (final char c : dest) {
      if (c > 0) {
        buf.append(c);
      }
    }

    return buf.toString();
  }

  /**
   * @param src source string
   * @param base base 10 or 62
   * @param powers powers values of this base
   * @return long representation of the string
   */
  protected static long strToLong(String src, int base, final long[] powers) {
    long ret = 0;
    final int nLen = src.length();
    int power;

    for (int i = 0; i < nLen; i++) {
      final char c = src.charAt(i);
      for (power = 0; power < base; power++) {

        // Find the character in the conversion table and add to the value.
        if (ALPHABET[power] == c) {
          ret += (power * powers[nLen - i - 1]);
          break;
        }
      }

      if (power == base) {
        LOGGER.warn("Character too big for base?");
        return -1;
      }
    }

    return ret;
  }

  /**
   * Get preferred timestamp seed, either provided or current date/time if null.
   *
   * @param ts timestamp to use or null for current date/time
   * @return Calendar set to preferred timestamp
   */
  protected static final Calendar getTimestampSeed(final Date ts) {
    final Calendar cal = Calendar.getInstance();

    if (ts != null) {
      cal.setTimeInMillis(ts.getTime());
    }

    return cal;
  }

  /**
   * Overload. Generate 10 character, base-62 key from given staff id and timestamp.
   *
   * @param staffId 3-char, base-62 staff id
   * @param ts timestamp to use or null for current date/time
   * @return generated 10 character, base-62 key
   */
  protected static String makeKey(final String staffId, final Date ts) {
    return makeKey(new StringKey(staffId), ts);
  }

  /**
   * Generate 10 character, base-62 key from given staff id and timestamp.
   *
   * @param wrap the wrap
   * @param ts timestamp to use or null for current date/time
   * @return the key from staffID
   */
  protected static String makeKey(final StringKey wrap, final Date ts) {
    try {
      ResourceParamValidator.validate(wrap);
      return createTimestampStr(ts).trim() + wrap.getValue();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  /**
   * Generate an identifier with the given staff id and current timestamp.
   *
   * @param staffId three char staff id
   * @param ts timestamp to use
   * @return unique key from staff id and timestamp
   */
  protected static String generate(String staffId, final Date ts) {
    return makeKey(!StringUtils.isBlank(staffId) ? staffId : DEFAULT_USER_ID, ts);
  }

  /**
   * Convert a 10 character, base 62 legacy key to base 10 in format 0000-0000-0000-0000000. Legacy
   * code refers to this as a UI identifier.
   *
   * @param key 10 character, base-62 legacy key
   * @return UI identifier in format 0000-0000-0000-0000000. If provided key is null or empty, then
   * null is returned.
   */
  public static String getUIIdentifierFromKey(String key) {
    if (StringUtils.isBlank(key)) {
      return null;
    }

    final String tsB62 = key.substring(0, LEN_KEYTIMESTAMP);
    final String staffB62 = key.substring(LEN_KEYTIMESTAMP);
    LOGGER.trace("strTimestamp={}, strStaffId={}", tsB62, staffB62);

    final String tsB10 =
      StringUtils.leftPad(String.valueOf(Base62.toBase10(tsB62)), LEN_UIIDTIMESTAMP, '0');
    final String staffB10 =
      StringUtils.leftPad(String.valueOf(Base62.toBase10(staffB62)), LEN_UIIDSTAFFID, '0');
    LOGGER.trace("tsB10={}, staffB10={}", tsB10, staffB10);

    final StringBuilder buf = new StringBuilder();
    buf.append(tsB10, 0, 4).append('-').append(tsB10, 4, 8).append('-').append(tsB10, 8, 12)
      .append('-').append(tsB10.substring(12)).append(staffB10);

    return buf.toString();
  }

  /**
   * Converts UIID to base62Key
   *
   * @param uiIdentifier - UIID
   * @return Base62 key
   */
  public static String getKeyFromUIIdentifier(String uiIdentifier) {
    if (StringUtils.isBlank(uiIdentifier)) {
      return null;
    }
    String noDashes = StringUtils.remove(uiIdentifier, '-');
    if (!noDashes.matches("\\d{19}")) {
      throw new IllegalArgumentException(
        "uiIdentifier must have 19 digits, actual:[" + uiIdentifier + "]");
    }
    long tsLong = Long.parseLong(noDashes.substring(0, LEN_UIIDTIMESTAMP));
    long staffIdLong = Long.parseLong(noDashes.substring(LEN_UIIDTIMESTAMP));
    String tsBase62 = StringUtils.leftPad(Base62.toBase62(tsLong), LEN_KEYTIMESTAMP, '0');
    String staffIdBase62 = StringUtils.leftPad(Base62.toBase62(staffIdLong), LEN_KEYSTAFFID, '0');
    String result = tsBase62 + staffIdBase62;
    if (result.length() > 10) {
      throw new IllegalArgumentException("uiIdentifier [" + uiIdentifier + "] is invalid. Conversion result [" + result
        + "] length is more then 10 characters");
    }
    return result;
  }

  /**
   * Extract the date portion from a CWS/CMS identifier.
   *
   * @param key - CWS/CMS identifier
   * @return date extracted from unique key
   */
  public static Date getDateFromKey(String key) {
    if (StringUtils.isBlank(key) || key.length() < LEN_KEYTIMESTAMP) {
      return null;
    }

    final String tsB62 = key.substring(0, LEN_KEYTIMESTAMP);
    long slong = strToLong(tsB62, 62, POWER_BASE62);
    long timestamp = longToTimestamp(slong);
    return new Date(timestamp);
  }

  private static final Map<String, StaffGate> gates =
    new PassiveExpiringMap<>(5, TimeUnit.MINUTES, new ConcurrentHashMap<>());

  /**
   * Synchronize key generation on staff/user id. The same user can only generate one key at a
   * time.
   *
   * @author CWDS API Team
   */
  static class StaffGate implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String staffId;

    private StaffGate(String staffId) {
      this.staffId = staffId;
    }

    /**
     * Synchronize briefly to acquire the user's key gate.
     *
     * @param staffId user's staff id
     * @return user's gate
     */
    public static synchronized StaffGate getStaffGate(String staffId) {
      StaffGate ret;
      if (gates.containsKey(staffId)) {
        ret = gates.get(staffId);
      } else {
        ret = new StaffGate(staffId);
        gates.put(staffId, ret);
      }

      return ret;
    }

    /**
     * Each unique user gets an instance of this class. Since this method is synchronized, each user
     * can only call getNextValue on one thread at a time, even for multiple simultaneous requests.
     *
     * @return generated CWS/CMS key
     */
    private synchronized String getNextValue() {
      String newValue;
      do {
        newValue = CmsKeyIdGenerator.generate(staffId, new Date());
      } while (lastKeys.containsKey(newValue)); // safety

      lastKeys.put(newValue, newValue);
      return newValue;
    }

    @Override
    public int hashCode() {
      return 31 + ((staffId == null) ? 0 : staffId.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      StaffGate other = (StaffGate) obj;
      if (staffId == null) {
        return other.staffId == null;
      } else {
        return staffId.equals(other.staffId);
      }
    }

  }

  /**
   * Self-validating bean class for staff id.
   *
   * <p>
   * javax.validation only works on real "bean" classes, not Java native classes like String or
   * Long. Therefore, wrap the incoming staff id in a small class, which follows the Java Bean
   * specification (i.e., getters and setters).
   *
   * @author CWDS API Team
   */
  public static final class StringKey {

    @NotNull
    @Size(min = 3, max = 3)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String value;

    /**
     * Constructor.
     *
     * @param value String to evaluate
     */
    public StringKey(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

  /**
   * Utility struct class stores details of CWDS key decomposition.
   *
   * <p>
   * <strong>WARNING</strong>: <strong>Do NOT change this struct!</strong> It maps directly the C++
   * library.
   * </p>
   */
  public static final class KeyDetail {

    public String key; // NOSONAR
    public String staffId; // NOSONAR
    public String UITimestamp; // NOSONAR
    public String PTimestamp; // NOSONAR
  }

}
