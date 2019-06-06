package gov.ca.cwds.rest.api.domain.cms;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;
import io.dropwizard.jackson.JsonSnakeCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * {@link DomainObject} representing a CMS System Code entry.
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel
public class SystemCode extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("system_id")
  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "123")
  private Short systemId;

  @JsonProperty("category_id")
  @ApiModelProperty(required = false, readOnly = false, example = "0")
  private Short categoryId;

  @JsonProperty("inactive_indicator")
  @ApiModelProperty(required = false, readOnly = false, example = "N")
  private String inactiveIndicator;

  @JsonProperty("other_cd")
  @ApiModelProperty(required = false, readOnly = false, example = "A")
  private String otherCd;

  @JsonProperty("short_description")
  @ApiModelProperty(required = false, readOnly = false, example = "Amador")
  private String shortDescription;

  @JsonProperty("logical_id")
  @ApiModelProperty(required = false, readOnly = false, example = "03")
  private String logicalId;

  @JsonProperty("third_id")
  @ApiModelProperty(required = false, readOnly = false, example = "0000")
  private String thirdId;

  @JsonProperty("foreign_key_meta_table")
  @NotEmpty
  @Size(min = 1, max = 20)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "GVR_ENTC")
  private String foreignKeyMetaTable;

  @JsonProperty("long_description")
  private String longDescription;

  public SystemCode() {
  }

  /**
   * Construct from all fields.
   * 
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
  @JsonCreator
  public SystemCode(@JsonProperty("system_id") Short systemId,
      @JsonProperty("category_id") Short categoryId,
      @JsonProperty("inactive_indicator") String inactiveIndicator,
      @JsonProperty("other_cd") String otherCd,
      @JsonProperty("short_description") String shortDescription,
      @JsonProperty("logical_id") String logicalId, @JsonProperty("third_id") String thirdId,
      @JsonProperty("foreign_key_meta_table") String foreignKeyMetaTable,
      @JsonProperty("long_description") String longDescription) {
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

  public void setSystemId(Short systemId) {
    this.systemId = systemId;
  }

  public void setCategoryId(Short categoryId) {
    this.categoryId = categoryId;
  }

  public void setInactiveIndicator(String inactiveIndicator) {
    this.inactiveIndicator = inactiveIndicator;
  }

  public void setOtherCd(String otherCd) {
    this.otherCd = otherCd;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public void setLogicalId(String logicalId) {
    this.logicalId = logicalId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public void setForeignKeyMetaTable(String foreignKeyMetaTable) {
    this.foreignKeyMetaTable = foreignKeyMetaTable;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  /**
   * @return the systemId
   */
  public Short getSystemId() {
    return systemId;
  }

  /**
   * @return the categoryId
   */
  public Short getCategoryId() {
    return categoryId;
  }

  /**
   * @return the inactiveIndicator
   */
  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  /**
   * @return the otherCd
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * @return the shortDescription
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * @return the logicalId
   */
  public String getLogicalId() {
    return logicalId;
  }

  /**
   * @return the thirdId
   */
  public String getThirdId() {
    return thirdId;
  }

  /**
   * @return the foreignKeyMetaTable
   */
  public String getForeignKeyMetaTable() {
    return foreignKeyMetaTable;
  }

  /**
   * @return the longDescription
   */
  public String getLongDescription() {
    return longDescription;
  }

  public SystemCodeDescriptor getSystemCodeDescriptor() {
    SystemCodeDescriptor desc = new SystemCodeDescriptor();
    desc.setId(getSystemId());
    desc.setDescription(getShortDescription());
    return desc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
