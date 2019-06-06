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
 * {@link DomainObject} representing a System Meta list response
 * 
 * @author CWDS API Team
 */
@JsonInclude(Include.ALWAYS)
public class SystemMetaListResponse extends ReportingDomain implements Response {

  private static final long serialVersionUID = 1L;

  @JsonProperty("system_metas")
  private Set<SystemMeta> systemMetas = new HashSet<>();

  /**
   * Default no-arg constructor.
   */
  public SystemMetaListResponse() {
    // nothing doing
  }

  /**
   * Construct with system meta set.
   * 
   * @param systemMetas System meta set
   */
  public SystemMetaListResponse(Set<SystemMeta> systemMetas) {
    this.systemMetas = systemMetas;
  }

  /**
   * Get system meta set.
   * 
   * @return System meta set
   */
  public Set<SystemMeta> getSystemMetas() {
    return systemMetas;
  }

  /**
   * Set system meta set.
   * 
   * @param systemMetas System meta set
   */
  public void setSystemMetas(Set<SystemMeta> systemMetas) {
    this.systemMetas = systemMetas;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
