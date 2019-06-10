package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parent.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ElasticSearchPersonParent extends ElasticSearchPersonNestedPerson {

  @JsonProperty("relationship")
  private String relationship;

  @Override
  @JsonProperty("legacy_id")
  // @Deprecated
  public String getLegacyClientId() {
    return super.legacyPersonId;
  }

  @Override
  @JsonProperty("legacy_last_updated")
  // @Deprecated
  public String getLegacyLastUpdated() {
    return super.legacyLastUpdated;
  }

  @Override
  @JsonProperty("legacy_source_table")
  // @Deprecated
  public String getLegacySourceTable() {
    return super.getLegacySourceTable();
  }

  public String getRelationship() {
    return relationship;
  }

  public void setRelationship(String relationship) {
    this.relationship = relationship;
  }

}
