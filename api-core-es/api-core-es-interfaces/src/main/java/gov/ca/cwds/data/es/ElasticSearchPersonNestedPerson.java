package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Screening nested person, such as reporter or assigned social worker.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonNestedPerson extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {
  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  /**
   * Optional field
   */
  @JsonProperty("sensitivity_indicator")
  private transient String sensitivityIndicator;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @JsonIgnore
  // @Deprecated
  protected String legacyPersonId;

  @JsonIgnore
  // @Deprecated
  protected String legacyLastUpdated;

  @JsonIgnore
  // @Deprecated
  private String legacySourceTable;

  /**
   * Getter for legacy person id. Child classes override the JSON field name, per the document
   * mapping.
   * 
   * @return legacy person id
   */
  // @Deprecated
  public String getLegacyClientId() {
    return legacyPersonId;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // @Deprecated
  public void setLegacyClientId(String legacyClientId) {
    this.legacyPersonId = legacyClientId;
  }

  // @Deprecated
  public String getLegacyLastUpdated() {
    return legacyLastUpdated;
  }

  // @Deprecated
  public void setLegacyLastUpdated(String legacyLastUpdated) {
    this.legacyLastUpdated = legacyLastUpdated;
  }

  // @Deprecated
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  // @Deprecated
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  public void setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
  }
}
