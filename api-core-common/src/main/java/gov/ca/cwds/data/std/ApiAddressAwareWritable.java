package gov.ca.cwds.data.std;

/**
 * Interface defines naming standard methods for persistence classes that represent Address. Allows
 * DAO and service classes to operate on Address-aware objects without knowledge of their
 * implementation.
 * 
 * <p>
 * Writable. Provides "setter" methods for all fields.
 * </p>
 * 
 * @author CWDS API Team
 */
public interface ApiAddressAwareWritable extends ApiAddressAware {

  /**
   * Setter for (first) street address
   * 
   * @param streetAddress (first) street address
   */
  void setStreetAddress(String streetAddress);

  /**
   * Setter for state, 2-char code.
   * 
   * @param state state
   */
  void setState(String state);

  /**
   * Setter for county
   * 
   * @param county the county
   */
  void setCounty(String county);

  /**
   * Setter for city
   * 
   * @param city city
   */
  void setCity(String city);

  /**
   * Setter for 5-digit zip
   * 
   * @param zip zip code
   */
  void setZip(String zip);

}
