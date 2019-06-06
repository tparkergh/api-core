package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Person CSEC history.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonCsec extends ApiObjectIdentity {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("end_date")
  private String endDate;

  @JsonProperty("csec_code_id")
  private String csecCodeId;

  @JsonProperty("description")
  private String csecDesc;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @SuppressWarnings("javadoc")
  public String getId() {
    return id;
  }

  @SuppressWarnings("javadoc")
  public void setId(String id) {
    this.id = id;
  }

  @SuppressWarnings("javadoc")
  public String getStartDate() {
    return startDate;
  }

  @SuppressWarnings("javadoc")
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @SuppressWarnings("javadoc")
  public String getEndDate() {
    return endDate;
  }

  @SuppressWarnings("javadoc")
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @SuppressWarnings("javadoc")
  public String getCsecCodeId() {
    return csecCodeId;
  }

  @SuppressWarnings("javadoc")
  public void setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
  }

  @SuppressWarnings("javadoc")
  public String getCsecDesc() {
    return csecDesc;
  }

  @SuppressWarnings("javadoc")
  public void setCsecDesc(String csecDesc) {
    this.csecDesc = csecDesc;
  }

  @SuppressWarnings("javadoc")
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  @SuppressWarnings("javadoc")
  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

}
