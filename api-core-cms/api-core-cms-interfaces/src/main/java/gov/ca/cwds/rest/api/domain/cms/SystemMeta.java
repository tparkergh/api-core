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
 * {@link DomainObject} representing a record from System Meta Table
 * 
 * @author CWDS API Team
 */
@JsonSnakeCase
@ApiModel
public class SystemMeta extends ReportingDomain implements Response {
  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("logical_table_dsd_name")
  @NotEmpty
  @Size(min = 1, max = 8)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "GVC_ENTC")
  private String logicalTableDsdName;

  @JsonProperty("user_table_name")
  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "",
      example = "Government Entity Type")
  private String userTableName;

  @JsonProperty("short_description_name")
  @NotEmpty
  @Size(min = 1, max = 40)
  @ApiModelProperty(required = true, readOnly = false, value = "", example = "Government Entity")
  private String shortDescriptionName;

  public SystemMeta() {
  }

  /**
   * Construct from all fields.
   * 
   * @param logicalTableDsdName the Logical Table DsdName
   * @param userTableName the UserTableName
   * @param shortDescriptionName the Short Description Name
   */
  @JsonCreator
  public SystemMeta(@JsonProperty("logical_table_dsd_name") String logicalTableDsdName,
      @JsonProperty("user_table_name") String userTableName,
      @JsonProperty("short_description_name") String shortDescriptionName) {
    super();
    this.logicalTableDsdName = logicalTableDsdName;
    this.userTableName = userTableName;
    this.shortDescriptionName = shortDescriptionName;
  }

  public void setLogicalTableDsdName(String logicalTableDsdName) {
    this.logicalTableDsdName = logicalTableDsdName;
  }

  public void setUserTableName(String userTableName) {
    this.userTableName = userTableName;
  }

  public void setShortDescriptionName(String shortDescriptionName) {
    this.shortDescriptionName = shortDescriptionName;
  }

  /**
   * @return the logicalTableDsdName
   */
  public String getLogicalTableDsdName() {
    return logicalTableDsdName;
  }

  /**
   * @return the userTableName
   */
  public String getUserTableName() {
    return userTableName;
  }

  /**
   * @return the shortDescriptionName
   */
  public String getShortDescriptionName() {
    return shortDescriptionName;
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
