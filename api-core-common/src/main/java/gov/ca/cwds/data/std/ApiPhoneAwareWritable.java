package gov.ca.cwds.data.std;

/**
 * Interface defines naming standard methods for persistence classes that represent phone numbers.
 * Allows DAO and service classes to operate on phone-aware objects efficiently, without possessing
 * knowledge of internal implementation details.
 * 
 * <p>
 * Writable. Provides "setter" methods for all fields.
 * </p>
 * 
 * @author CWDS API Team
 */
public interface ApiPhoneAwareWritable extends ApiPhoneAware {

  /**
   * Setter for concatenated phone number.
   * 
   * @param phoneNumber phone number
   */
  void setPhoneNumber(String phoneNumber);

  /**
   * Setter for phone number extension.
   * 
   * @param phoneNumberExtension phone number extension
   */
  void getPhoneNumberExtension(String phoneNumberExtension);

  /**
   * Setter for phone number type.
   * 
   * @param phoneType phone number type
   */
  void setPhoneType(PhoneType phoneType);

}
