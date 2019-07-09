package gov.ca.cwds.data.es;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Legacy descriptor.
 * 
 * @author CWDS API Team
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ElasticSearchLegacyDescriptor extends ApiObjectIdentity {

  private static final long serialVersionUID = 2802094735397568904L;

  @JsonProperty("legacy_id")
  private String legacyId;

  @JsonProperty("legacy_ui_id")
  private String legacyUiId;

  @JsonProperty("legacy_last_updated")
  private String legacyLastUpdated;

  @JsonProperty("legacy_table_name")
  private String legacyTableName;

  @JsonProperty("legacy_table_description")
  private String legacyTableDescription;

  /**
   * Default, no-arg constructor.
   */
  public ElasticSearchLegacyDescriptor() {
    // Default, no-op.
  }

  /**
   * Create from all fields.
   * 
   * @param legacyId Legacy ID
   * @param legacyUiId Legacy UI ID
   * @param legacyLastUpdated Legacy last updated time stamp
   * @param legacyTableName Legacy table name
   * @param legacyTableDescription Legacy table description
   */
  public ElasticSearchLegacyDescriptor(String legacyId, String legacyUiId, String legacyLastUpdated,
      String legacyTableName, String legacyTableDescription) {
    super();
    this.legacyId = legacyId;
    this.legacyUiId = legacyUiId;
    this.legacyLastUpdated = legacyLastUpdated;
    this.legacyTableName = legacyTableName;
    this.legacyTableDescription = legacyTableDescription;
  }

  public String getLegacyId() {
    return legacyId;
  }

  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  public String getLegacyUiId() {
    return legacyUiId;
  }

  public void setLegacyUiId(String legacyUiId) {
    this.legacyUiId = legacyUiId;
  }

  public String getLegacyLastUpdated() {
    return legacyLastUpdated;
  }

  public void setLegacyLastUpdated(String legacyLastUpdated) {
    this.legacyLastUpdated = legacyLastUpdated;
  }

  public String getLegacyTableName() {
    return legacyTableName;
  }

  public void setLegacyTableName(String legacyTableName) {
    this.legacyTableName = legacyTableName;
  }

  public String getLegacyTableDescription() {
    return legacyTableDescription;
  }

  public void setLegacyTableDescription(String legacyTableDescription) {
    this.legacyTableDescription = legacyTableDescription;
  }

  @JsonProperty("legacy_ui_id_flat")
  public String getLegacyUiIdFlat() {
    return StringUtils.isNotBlank(getLegacyUiId()) ? getLegacyUiId().trim().replaceAll("[^0-9]", "")
        : null;
  }
}
