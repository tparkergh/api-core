package gov.ca.cwds.data.legacy.cms.entity.facade;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.NamedNativeQuery;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * The POJO is intended to contain data from different tables and to be filled by
 * {@link SqlResultSetMapping} after {@link NamedNativeQuery} is invoked.
 *
 * <p>
 * The reason to use {@link NamedNativeQuery} and to create the 'facade' class is a performance
 * issue with lots of joins when using {@link Entity} classes.
 * </p>
 *
 * <p>
 * N.B. {@link NamedNativeQuery} and {@link SqlResultSetMapping} are placed in
 * {@link gov.ca.cwds.data.legacy.cms.entity.Case} {@link Entity} class as they can be found by
 * hibernate in {@link Entity} classes.
 * </p>
 *
 * @author CWDS TPT-2 Team
 */
@SuppressWarnings("squid:S00107")
public class ClientByStaff implements ApiMarker {

  private static final long serialVersionUID = 1L;

  public static final String CASE_FIND_CLIENTS_BY_STAFF_ID =
      "ClientByStaff.caseFindClientsByStaffId";
  public static final String REFERRAL_FIND_CLIENTS_BY_STAFF_ID =
      "ClientByStaff.referralFindClientsByStaffId";
  public static final String MAPPING_CLIENT_FROM_CASE = "ClientByStaff.caseMapping";
  public static final String MAPPING_CLIENT_FROM_REFERRAL = "ClientByStaff.referralMapping";

  private String identifier;
  private String firstName;
  private String middleName;
  private String lastName;
  private String nameSuffix;
  private String sensitivityType;
  private LocalDate birthDate;
  private LocalDate casePlanReviewDueDate;

  /**
   * Constructor with 7+ params.
   * 
   * @param identifier primary key
   * @param firstName first name
   * @param middleName middle name
   * @param lastName last name
   * @param nameSuffix suffix
   * @param sensitivityType client sensitivity (R = restricted/sealed, S = sensitive, N = no
   *        restriction)
   * @param birthDate birth date
   * @param casePlanReviewDueDate case plan review date
   */
  public ClientByStaff(String identifier, String firstName, String middleName, String lastName,
      String nameSuffix, String sensitivityType, LocalDate birthDate,
      LocalDate casePlanReviewDueDate) {
    this.identifier = identifier;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.nameSuffix = nameSuffix;
    this.sensitivityType = sensitivityType;
    this.birthDate = birthDate;
    this.casePlanReviewDueDate = casePlanReviewDueDate;
  }

  public ClientByStaff(String identifier, String firstName, String middleName, String lastName,
      String nameSuffix, String sensitivityType, LocalDate birthDate) {
    this(identifier, firstName, middleName, lastName, nameSuffix, sensitivityType, birthDate, null);
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  public String getSensitivityType() {
    return sensitivityType;
  }

  public void setSensitivityType(String sensitivityType) {
    this.sensitivityType = sensitivityType;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public LocalDate getCasePlanReviewDueDate() {
    return casePlanReviewDueDate;
  }

  public void setCasePlanReviewDueDate(LocalDate casePlanReviewDueDate) {
    this.casePlanReviewDueDate = casePlanReviewDueDate;
  }
}
