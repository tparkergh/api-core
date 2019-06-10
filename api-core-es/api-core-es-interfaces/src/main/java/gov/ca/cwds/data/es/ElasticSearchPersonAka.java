package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * AKA ("also known as") for "other client name".
 * 
 * @author CWDS API Team
 */
@JsonPropertyOrder(alphabetic = true)
public class ElasticSearchPersonAka extends ApiObjectIdentity {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("middle_name")
  private String middleName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("name_type")
  private String nameType;

  @JsonProperty("prefix")
  private String prefix;

  @JsonProperty("suffix")
  private String suffix;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  @SuppressWarnings("javadoc")
  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  @SuppressWarnings("javadoc")
  public String getFirstName() {
    return firstName;
  }

  @SuppressWarnings("javadoc")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @SuppressWarnings("javadoc")
  public String getMiddleName() {
    return middleName;
  }

  @SuppressWarnings("javadoc")
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @SuppressWarnings("javadoc")
  public String getLastName() {
    return lastName;
  }

  @SuppressWarnings("javadoc")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @SuppressWarnings("javadoc")
  public String getNameType() {
    return nameType;
  }

  @SuppressWarnings("javadoc")
  public void setNameType(String nameType) {
    this.nameType = nameType;
  }

  @SuppressWarnings("javadoc")
  public String getPrefix() {
    return prefix;
  }

  @SuppressWarnings("javadoc")
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  @SuppressWarnings("javadoc")
  public String getSuffix() {
    return suffix;
  }

  @SuppressWarnings("javadoc")
  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  @SuppressWarnings("javadoc")
  public String getId() {
    return id;
  }

  @SuppressWarnings("javadoc")
  public void setId(String id) {
    this.id = id;
  }
}
