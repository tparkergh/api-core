package gov.ca.cwds.data.persistence.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a CMS System Code record.
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SystemCode.findByForeignKeyMetaTable",
    query = "FROM SystemCode WHERE foreignKeyMetaTable = :foreignKeyMetaTable")
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SystemCode.findBySystemCodeId",
    query = "FROM SystemCode WHERE systemId = :systemId")
@Entity
@Table(name = "SYS_CD_C")
public class SystemCode extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Type(type = "short")
  @Column(name = "SYS_ID")
  private Short systemId;

  @Type(type = "short")
  @Column(name = "CATEGORYID")
  private Short categoryId;

  @Column(name = "INACTV_IND")
  private String inactiveIndicator;

  @Column(name = "OTHER_CD")
  @ColumnTransformer(read = "trim(OTHER_CD)")
  private String otherCd;

  @Column(name = "SHORT_DSC")
  @ColumnTransformer(read = "trim(SHORT_DSC)")
  private String shortDescription;

  @Column(name = "LGC_ID")
  @ColumnTransformer(read = "trim(LGC_ID)")
  private String logicalId;

  @Column(name = "THIRD_ID")
  private String thirdId;

  @Column(name = "FKS_META_T")
  private String foreignKeyMetaTable;

  @Column(name = "LONG_DSC")
  @ColumnTransformer(read = "trim(LONG_DSC)")
  private String longDescription;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public SystemCode() {
    super();
  }

  /**
   * @param systemId - The System Id
   * @param categoryId - the category id
   * @param inactiveIndicator - inactive indicator
   * @param otherCd - other CD
   * @param shortDescription - the short description
   * @param logicalId - the logical Id
   * @param thirdId - third Id
   * @param foreignKeyMetaTable - - foreign key to system meta table (S_META_T)
   * @param longDescription - long description
   */
  @SuppressWarnings("squid:S00107")
  public SystemCode(Short systemId, Short categoryId, String inactiveIndicator, String otherCd,
      String shortDescription, String logicalId, String thirdId, String foreignKeyMetaTable,
      String longDescription) {
    super();
    this.systemId = systemId;
    this.categoryId = categoryId;
    this.inactiveIndicator = inactiveIndicator;
    this.otherCd = otherCd;
    this.shortDescription = shortDescription;
    this.logicalId = logicalId;
    this.thirdId = thirdId;
    this.foreignKeyMetaTable = foreignKeyMetaTable;
    this.longDescription = longDescription;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getSystemId().toString();
  }

  /**
   * @return serialVersionUID
   */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  /**
   * @return system code (unique)
   */
  public Short getSystemId() {
    return systemId;
  }

  /**
   * @return category ID
   */
  public Short getCategoryId() {
    return categoryId;
  }

  /**
   * @return inactive indicator
   */
  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  /**
   * @return other CD
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * @return short description
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * @return logical ID
   */
  public String getLogicalId() {
    return logicalId;
  }

  /**
   * @return third ID (not used in legacy DB)
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return foreign key to the System Meta Table
   */
  public String getForeignKeyMetaTable() {
    return foreignKeyMetaTable;
  }

  /**
   * @return long description
   */
  public String getLongDescription() {
    return longDescription;
  }

}
