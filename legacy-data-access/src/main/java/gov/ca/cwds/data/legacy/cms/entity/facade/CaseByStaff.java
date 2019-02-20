package gov.ca.cwds.data.legacy.cms.entity.facade;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.NamedNativeQuery;

import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType;
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
 * @author CWDS TPT-3 Team
 */
public class CaseByStaff implements ApiMarker {

  private static final long serialVersionUID = 1L;

  public static final String NATIVE_FIND_CASES_BY_STAFF_ID = "CaseByStaff.nativeFindCasesByStaffId";
  public static final String MAPPING_CASE_BY_STAFF = "CaseByStaff.mapping";

  private String identifier;
  private String caseName;
  private String clientIdentifier;
  private String clientFirstName;
  private String clientLastName;
  private String activeServiceComponent;
  private String assignmentIdentifier;
  private AssignmentType assignmentType;
  private LocalDate assignmentStartDate;

  /**
   * The constructor is required by @SqlResultSetMapping
   * 
   * @param identifier primary key
   * @param caseName case name
   * @param clientIdentifier client foreign key
   * @param clientFirstName client first name
   * @param clientLastName client last name
   * @param activeServiceComponent active service component
   * @param assignmentIdentifier assignment foreign key
   * @param assignmentTypeCode assignment type
   * @param assignmentStartDate assignment start date
   */
  @SuppressWarnings("squid:S00107")
  public CaseByStaff(String identifier, String caseName, String clientIdentifier,
      String clientFirstName, String clientLastName, String activeServiceComponent,
      String assignmentIdentifier, String assignmentTypeCode, LocalDate assignmentStartDate) {
    this.identifier = identifier;
    this.caseName = caseName;
    this.clientIdentifier = clientIdentifier;
    this.clientFirstName = clientFirstName;
    this.clientLastName = clientLastName;
    this.activeServiceComponent = activeServiceComponent;
    this.assignmentIdentifier = assignmentIdentifier;
    this.assignmentType = AssignmentType.fromCode(assignmentTypeCode);
    this.assignmentStartDate = assignmentStartDate;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getCaseName() {
    return caseName;
  }

  public void setCaseName(String caseName) {
    this.caseName = caseName;
  }

  public String getClientIdentifier() {
    return clientIdentifier;
  }

  public void setClientIdentifier(String clientIdentifier) {
    this.clientIdentifier = clientIdentifier;
  }

  public String getClientFirstName() {
    return clientFirstName;
  }

  public void setClientFirstName(String clientFirstName) {
    this.clientFirstName = clientFirstName;
  }

  public String getClientLastName() {
    return clientLastName;
  }

  public void setClientLastName(String clientLastName) {
    this.clientLastName = clientLastName;
  }

  public String getActiveServiceComponent() {
    return activeServiceComponent;
  }

  public void setActiveServiceComponent(String activeServiceComponent) {
    this.activeServiceComponent = activeServiceComponent;
  }

  public String getAssignmentIdentifier() {
    return assignmentIdentifier;
  }

  public void setAssignmentIdentifier(String assignmentIdentifier) {
    this.assignmentIdentifier = assignmentIdentifier;
  }

  public AssignmentType getAssignmentType() {
    return assignmentType;
  }

  public void setAssignmentType(AssignmentType assignmentType) {
    this.assignmentType = assignmentType;
  }

  public LocalDate getAssignmentStartDate() {
    return assignmentStartDate;
  }

  public void setAssignmentStartDate(LocalDate assignmentStartDate) {
    this.assignmentStartDate = assignmentStartDate;
  }
}
