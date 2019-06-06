package gov.ca.cwds.rest.api.domain.cms;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;
import gov.ca.cwds.rest.api.domain.ReportingDomain;

/**
 * {@link DomainObject} representing a system code list response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class SystemCodeListResponse extends ReportingDomain implements Response {

  private static final long serialVersionUID = -4392912596188537533L;

  @JsonProperty("system_codes")
  private Set<SystemCode> systemCodes = new HashSet<>();

  /**
   * Default no-arg constructor.
   */
  public SystemCodeListResponse() {}

  /**
   * Construct with args
   * 
   * @param systemCodes Set of system codes
   */
  public SystemCodeListResponse(Set<SystemCode> systemCodes) {
    this.systemCodes = systemCodes;
  }

  /**
   * Get set of system codes.
   * 
   * @return Set of system codes
   */
  public Set<SystemCode> getSystemCodes() {
    return systemCodes;
  }

  /**
   * Set system codes
   * 
   * @param systemCodes Set of system codes.
   */
  public void setSystemCodes(Set<SystemCode> systemCodes) {
    this.systemCodes = systemCodes;
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
