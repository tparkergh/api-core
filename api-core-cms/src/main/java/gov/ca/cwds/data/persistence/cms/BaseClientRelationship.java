package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Client Relationship.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class BaseClientRelationship extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Column(name = "ABSENT_CD")
  private String absentParentCode;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "CLNTRELC")
  private Short clientRelationshipType;

  @Type(type = "date")
  @Column(name = "END_DT")
  private Date endDate;

  @Column(name = "FKCLIENT_0")
  private String secondaryClientId;

  @Column(name = "FKCLIENT_T")
  private String primaryClientId;

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "SAME_HM_CD")
  private String sameHomeCode;

  @Type(type = "date")
  @Column(name = "START_DT")
  private Date startDate;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public BaseClientRelationship() {
    super();
  }

  /**
   * Constructor
   * 
   * @param absentParentCode indicates if the parent CLIENT is absent for the child with whom the
   *        relationship is being defined (N)
   * @param clientRelationshipType Client Relationship Type from System Code table
   * @param endDate date the relationship ended
   * @param secondaryClientId Mandatory Foreign key that includes a secondary individual as a CLIENT
   * @param primaryClientId Mandatory Foreign key that includes a primary individual as a CLIENT
   * @param id unique key
   * @param sameHomeCode indicates whether the two CLIENTs live in the same home (Y)
   * @param startDate date the relationship began
   */
  @SuppressWarnings("squid:S00107")
  public BaseClientRelationship(String absentParentCode, Short clientRelationshipType, Date endDate,
      String secondaryClientId, String primaryClientId, String id, String sameHomeCode,
      Date startDate) {
    super();
    this.absentParentCode = absentParentCode;
    this.clientRelationshipType = clientRelationshipType;
    this.endDate = endDate;
    this.secondaryClientId = secondaryClientId;
    this.primaryClientId = primaryClientId;
    this.id = id;
    this.sameHomeCode = sameHomeCode;
    this.startDate = startDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.api.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  /**
   * @return the absentParentCode
   */
  public String getAbsentParentCode() {
    return absentParentCode;
  }

  /**
   * @return the clientRelationshipType
   */
  public Short getClientRelationshipType() {
    return clientRelationshipType;
  }

  /**
   * @return the endDate
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * @return the secondaryClientId
   */
  public String getSecondaryClientId() {
    return secondaryClientId;
  }

  /**
   * @return the primaryClientId
   */
  public String getPrimaryClientId() {
    return primaryClientId;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the sameHomeCode
   */
  public String getSameHomeCode() {
    return StringUtils.trimToEmpty(sameHomeCode);
  }

  /**
   * @return the startDate
   */
  public Date getStartDate() {
    return startDate;
  }

}
