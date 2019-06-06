package gov.ca.cwds.data.es;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Screening person for "all_people" element.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ElasticSearchPersonAny extends ElasticSearchPersonNestedPerson {
  
  @JsonProperty("legacy_source_table")
  // @Deprecated
  private String legacySourceTable;

  private List<String> roles = new ArrayList<>();

  @Override
  @JsonProperty("legacy_id")
  // @Deprecated
  public String getLegacyClientId() {
    return legacyPersonId;
  }

  @Override
  // @Deprecated
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  @Override
  // @Deprecated
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

}
