package gov.ca.cwds.data.persistence.cms;

/**
 * Common base-62 algorithm. Varied flavors online. Customized for CMS legacy keys. Converts base-10
 * integral number to base-62 and vice versa.
 * 
 * @author CWDS API Team
 */
public final class Base62 {

  /**
   * Base-62 character set.
   */
  public static final String ALPHABET =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  private static final int BASE = ALPHABET.length();

  private Base62() {}

  /**
   * Convert a base-10 number to a base-62 String.
   * 
   * @param i base-10 number
   * @return base-62 String
   */
  public static String toBase62(long i) {
    final StringBuilder sb = new StringBuilder();
    if (i == 0) {
      return "a";
    }
    long j = i;
    while (j > 0) {
      j = fromBase10(j, sb);
    }
    return sb.reverse().toString();
  }

  private static long fromBase10(long i, final StringBuilder sb) {
    long rem = i % BASE;
    sb.append(ALPHABET.charAt((int) rem));
    return i / BASE;
  }

  /**
   * Convert a base-62 String to a base-10 number.
   * 
   * @param str base-62 String
   * @return base-10 number
   */
  public static long toBase10(String str) {
    return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
  }

  private static long toBase10(final char[] chars) {
    long n = 0;
    for (int i = chars.length - 1; i >= 0; i--) {
      n += toBase10(ALPHABET.indexOf(chars[i]), i);
    }
    return n;
  }

  private static long toBase10(long n, int pow) {
    return n * (long) Math.pow(BASE, pow);
  }
}

