package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 *
 * Any general comments regarding a particular PLACEMENT HOME, documented description and associated
 * resolution of dissatisfaction about an occurrence, or feature of a licensed or nonlicensed home,
 * which has been brought to the attention of a STAFF PERSON. This general information are used when
 * reviewing homes for  potential placements.  It is the social worker's  responsibility to
 * communicate a complaint to the  licensing workers for investigation when deem  necessary.
 */
@Entity
@Table(name = "HMNOTE_T")
@SuppressWarnings({"squid:S3437"}) //LocalDate is serializable
public class PlacementHomeNotes implements PersistentObject {

  private static final long serialVersionUID = -6583999584863835466L;

  /**
   * ID - A system generated number used to uniquely identify each COMPLAINT. This ID is composed of
   * a base 62 Creation Timestamp and the STAFF_PERSON ID (a sequential 3 digit base 62 number
   * generated by the system). This value eliminates the need for an additional set of Creation
   * Timestamp and Creation User ID which is needed to satisfy the Audit Trail requirement.
   */
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  /**
   * RECEIVED_DATE - The date a complaint or this particular documented information is received by a
   * CWS office.
   */
  @Basic
  @Column(name = "RECEIVE_DT", nullable = false)
  private LocalDate receiveDt;

  /**
   * REFERRED_TO_LICENSING_IND - This indicates whether this particular PLACEMENT HOME complaint has
   * been referred to the Licensing Department or CCL for investigation (Y) or not (N).
   */
  @Basic
  @Column(name = "REF_LICIND", nullable = false, length = 1)
  private String refLicind;

  /**
   * SUBMITTER_NAME - The name of the person who formally or informally informs a STAFF PERSON of a
   * complaint or any additional information related to a specific licensed or nonlicensed home.
   */
  @Basic
  @Column(name = "SUBMITR_NM", nullable = false, length = 35)
  private String submitrNm;

  /**
   * LAST_UPDATE_ID - The ID (a sequential 3 digit base 62 number generated by the system) of the
   * STAFF PERSON or batch program which made the last update to an occurrence of this entity type.
   */
  @Basic
  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
  private String lstUpdId;

  /**
   * LAST_UPDATE_TIMESTAMP - The date and time of the most recent update to an occurrence of this
   * entity type.
   */
  @Basic
  @Column(name = "LST_UPD_TS", nullable = false)
  private LocalDateTime lstUpdTs;

  /**
   * FKPLC_HM_T - Mandatory Foreign key that PROVIDES_ADDITIONAL_INFO_ABOUT a PLACEMENT_HOME.
   */
  @Basic
  @Column(name = "FKPLC_HM_T", nullable = false, length = 10)
  private String fkplcHmT;

  /**
   * COMMENT_DESCRIPTION - General narrative text which records details or resolution of general
   * concerns about a particular PLACEMENT_HOME.
   */
  @Basic
  @Column(name = "COMNT_DSC", nullable = false, length = 254)
  private String comntDsc;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public LocalDate getReceiveDt() {
    return receiveDt;
  }

  public void setReceiveDt(LocalDate receiveDt) {
    this.receiveDt = receiveDt;
  }

  public String getRefLicind() {
    return refLicind;
  }

  public void setRefLicind(String refLicind) {
    this.refLicind = refLicind;
  }

  public String getSubmitrNm() {
    return submitrNm;
  }

  public void setSubmitrNm(String submitrNm) {
    this.submitrNm = submitrNm;
  }

  public String getLstUpdId() {
    return lstUpdId;
  }

  public void setLstUpdId(String lstUpdId) {
    this.lstUpdId = lstUpdId;
  }

  public LocalDateTime getLstUpdTs() {
    return lstUpdTs;
  }

  public void setLstUpdTs(LocalDateTime lstUpdTs) {
    this.lstUpdTs = lstUpdTs;
  }

  public String getFkplcHmT() {
    return fkplcHmT;
  }

  public void setFkplcHmT(String fkplcHmT) {
    this.fkplcHmT = fkplcHmT;
  }

  public String getComntDsc() {
    return comntDsc;
  }

  public void setComntDsc(String comntDsc) {
    this.comntDsc = comntDsc;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }
}
