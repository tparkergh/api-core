package gov.ca.cwds.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.utils.RomanNumerals;

/**
 * Functions to translate name suffix
 * 
 * @author CWDS API Team
 */
public class NameSuffixTranslator {

  private static final int MIN_ROMAN_NUMBER = 2;
  private static final int MAX_ROMAN_NUMBER = 50;

  /**
   * Name suffix based on predefined strings
   */
  public enum AlphaNameSuffix {

    ESQ("Esq", new String[] {"esq", "eq", "esqu"}),

    JR("Jr", new String[] {"jr", "junior", "jnr"}),

    SR("Sr", new String[] {"sr", "senior", "snr"}),

    MD("MD", new String[] {"md"}),

    PHD("PhD", new String[] {"phd"}),

    JD("JD", new String[] {"jd"});

    private String translatedValue;
    private Set<String> sourceValues;

    private static Map<String, AlphaNameSuffix> sourceMap = buildSourceMap();

    /**
     * AlphaNameSuffix constructor.
     * 
     * @param translatedValue The translated value
     * @param sourceValues The source values
     */
    private AlphaNameSuffix(String translatedValue, String[] sourceValues) {
      this.translatedValue = translatedValue;
      this.sourceValues = Arrays.stream(sourceValues).collect(Collectors.toSet());
    }

    private static Map<String, AlphaNameSuffix> buildSourceMap() {
      Map<String, AlphaNameSuffix> sourceMap = new HashMap<>();
      for (AlphaNameSuffix e : AlphaNameSuffix.values()) {
        for (String sourceValue : e.getSourceValues()) {
          sourceMap.put(sourceValue, e);
        }
      }
      return sourceMap;
    }

    /**
     * Get the translated value
     * 
     * @return The translated value
     */
    public String getTranslatedValue() {
      return translatedValue;
    }

    /**
     * Get set of source values
     * 
     * @return The set of source values
     */
    public Set<String> getSourceValues() {
      return sourceValues;
    }

    /**
     * Translate given source value to standard value
     * 
     * @param sourceValue The source value
     * @return Translated stanadatd value
     */
    public static String translate(String sourceValue) {
      if (StringUtils.isBlank(sourceValue)) {
        return null;
      }

      String retval = null;

      String manipulatedSourceValue = sourceValue.trim().toLowerCase();
      manipulatedSourceValue = manipulatedSourceValue.replaceAll("[^A-Za-z0-9]", "");

      AlphaNameSuffix suffix = sourceMap.get(manipulatedSourceValue);

      if (suffix != null) {
        retval = suffix.getTranslatedValue();
      }
      return retval;
    }
  }

  /**
   * Translate given name suffix to standard value if given nameSuffix can be translated, otherwise
   * return the given value.
   * 
   * @param nameSuffix The name suffix to translate
   * @return The standard translated value if it can be translated, otherwise returns the provided
   *         name suffix as is.
   */
  public static String translate(String nameSuffix) {
    if (StringUtils.isBlank(nameSuffix)) {
      return nameSuffix;
    }

    String alphaTranslation = AlphaNameSuffix.translate(nameSuffix);
    if (StringUtils.isNotBlank(alphaTranslation)) {
      return alphaTranslation;
    }


    String manipulatedNameSuffix = StringUtils.trimToEmpty(nameSuffix);
    manipulatedNameSuffix = StringUtils.removeEndIgnoreCase(manipulatedNameSuffix, "nd");
    manipulatedNameSuffix = StringUtils.removeEndIgnoreCase(manipulatedNameSuffix, "rd");
    manipulatedNameSuffix = StringUtils.removeEndIgnoreCase(manipulatedNameSuffix, "th");

    if (!StringUtils.isNumeric(manipulatedNameSuffix)) {
      return nameSuffix;
    }

    int nameSuffixInt = Integer.parseInt(manipulatedNameSuffix);

    if (nameSuffixInt < MIN_ROMAN_NUMBER || nameSuffixInt > MAX_ROMAN_NUMBER) {
      return nameSuffix;
    }

    return RomanNumerals.toRoman(nameSuffixInt);
  }
}
