package gov.ca.cwds.data.es;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.data.std.ApiPhoneAware;
import gov.ca.cwds.data.std.ApiPhoneAwareWritable;

/**
 * Simplistic, all-String representation of ES person.phone_numbers.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonPhone
    implements ApiMarker, ApiTypedIdentifier<String>, ApiPhoneAwareWritable {

  private static final long serialVersionUID = 1L;

  private String id;

  @JsonProperty("number")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String phoneNumber;

  /**
   * Not specified by Intake Person Auto-complete YAML.
   */
  @JsonIgnore
  private String phoneNumberExtension;

  @JsonProperty("type")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ApiPhoneAware.PhoneType phoneType;

  /**
   * Default constructor.
   */
  public ElasticSearchPersonPhone() {
    // Default, no-op.
  }

  /**
   * Construct a phone from a phone-aware object.
   * 
   * @param other another phone object
   */
  public ElasticSearchPersonPhone(ApiPhoneAware other) {
    if (other != null) {
      if (StringUtils.isNotBlank(other.getPhoneId())) {
        this.id = other.getPhoneId();
      }
      this.phoneNumber = other.getPhoneNumber();
      this.phoneType = other.getPhoneType();
    }
  }

  @JsonIgnore
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  @JsonIgnore
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  @Override
  @JsonIgnore
  public String getPhoneNumberExtension() {
    return this.phoneNumberExtension;
  }

  @Override
  @JsonIgnore
  public PhoneType getPhoneType() {
    return this.phoneType;
  }

  @Override
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @JsonIgnore
  @Override
  public void getPhoneNumberExtension(String phoneNumberExtension) {
    this.phoneNumberExtension = phoneNumberExtension;
  }

  @Override
  public void setPhoneType(PhoneType phoneType) {
    this.phoneType = phoneType;
  }

  @JsonIgnore
  @Override
  public String getPhoneId() {
    return this.id;
  }

  public void setPhoneNumberExtension(String phoneNumberExtension) {
    this.phoneNumberExtension = phoneNumberExtension;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
