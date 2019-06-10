package gov.ca.cwds.data.std;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Interface defines naming standard methods for persistence classes that represent phone numbers.
 * Allows DAO and service classes to operate on phone-aware objects efficiently, without possessing
 * knowledge of internal implementation details.
 * 
 * @author CWDS API Team
 */
public interface ApiPhoneAware extends ApiMarker {

  /**
   * Common phone types.
   * 
   * @author CWDS API Team
   */
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  public enum PhoneType {

    /**
     * Cell phone.
     */
    Cell, // NOSONAR

    /**
     * Work phone.
     */
    Work, // NOSONAR

    /**
     * Home phone, as in "E.T. home phone". Land line.
     */
    Home, // NOSONAR

    /**
     * Unknown phone type. Maybe one of those "no contract" deals from Walmart. Voice over IP. Home
     * line listed as emergency contact. Can be anything.
     */
    Other // NOSONAR
  }

  /**
   * Getter for concatenated phone number.
   * 
   * @return phone number
   */
  String getPhoneNumber();

  /**
   * Getter for phone number extension.
   * 
   * @return phone number extension
   */
  String getPhoneNumberExtension();

  /**
   * Getter for phone number type.
   * 
   * @return phone number type
   */
  PhoneType getPhoneType();

  /**
   * Getter for phone identifier (primary key).
   * 
   * @return phone identifier (primary key), if any
   */
  String getPhoneId();

}
