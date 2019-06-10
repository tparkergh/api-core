package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Screening reporter.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ElasticSearchPersonReporter extends ElasticSearchPersonNestedPerson {

  @Override
  @JsonProperty("legacy_reporter_id")
  // @Deprecated
  public String getLegacyClientId() {
    return legacyPersonId;
  }
}