package gov.ca.cwds.data.es;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Referral.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonReferral extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("legacy_referral_id")
  // @Deprecated
  private String legacyId;

  @JsonProperty("legacy_last_updated")
  // @Deprecated
  private String legacyLastUpdated;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("end_date")
  private String endDate;

  @JsonProperty("county_name")
  private String countyName;

  @JsonProperty("county_id")
  private String countyId;

  @JsonProperty("response_time")
  private String responseTime;

  @JsonProperty("response_time_id")
  private String responseTimeId;

  @JsonProperty("reporter")
  private ElasticSearchPersonReporter reporter = new ElasticSearchPersonReporter();

  @JsonProperty("assigned_social_worker")
  private ElasticSearchPersonSocialWorker assignedSocialWorker =
      new ElasticSearchPersonSocialWorker();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("allegations")
  private List<ElasticSearchPersonAllegation> allegations = new ArrayList<>();

  @JsonProperty("access_limitation")
  private ElasticSearchAccessLimitation accessLimitation = new ElasticSearchAccessLimitation();

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @JsonIgnore
  // @Deprecated
  public String getLegacyId() {
    return legacyId;
  }

  // @Deprecated
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  // @Deprecated
  public String getLegacyLastUpdated() {
    return legacyLastUpdated;
  }

  // @Deprecated
  public void setLegacyLastUpdated(String legacyLastUpdated) {
    this.legacyLastUpdated = legacyLastUpdated;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getCountyName() {
    return countyName;
  }

  public void setCountyName(String countyName) {
    this.countyName = countyName;
  }

  public String getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(String responseTime) {
    this.responseTime = responseTime;
  }

  public ElasticSearchPersonReporter getReporter() {
    return reporter;
  }

  public void setReporter(ElasticSearchPersonReporter reporter) {
    this.reporter = reporter;
  }

  public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  public List<ElasticSearchPersonAllegation> getAllegations() {
    return allegations;
  }

  public void setAllegations(List<ElasticSearchPersonAllegation> allegations) {
    this.allegations = allegations;
  }

  public ElasticSearchAccessLimitation getAccessLimitation() {
    return accessLimitation;
  }

  public void setAccessLimitation(ElasticSearchAccessLimitation accessLimitation) {
    this.accessLimitation = accessLimitation;
  }

  public String getCountyId() {
    return countyId;
  }

  public void setCountyId(String countyId) {
    this.countyId = countyId;
  }

  public String getResponseTimeId() {
    return responseTimeId;
  }

  public void setResponseTimeId(String responseTimeId) {
    this.responseTimeId = responseTimeId;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }
}
