package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Child.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ElasticSearchPersonChild extends ElasticSearchPersonNestedPerson {

  /**
   * Legacy CMS client id.
   * 
   * @deprecated use legacy descriptor
   */
  @Override
  @JsonProperty("legacy_client_id")
  @Deprecated
  public String getLegacyClientId() {
    return legacyPersonId;
  }

  /**
   * Legacy CMS last updated staff id.
   * 
   * @deprecated use legacy descriptor
   */
  @Override
  @JsonProperty("legacy_last_updated")
  @Deprecated
  public String getLegacyLastUpdated() {
    return legacyLastUpdated;
  }

}
