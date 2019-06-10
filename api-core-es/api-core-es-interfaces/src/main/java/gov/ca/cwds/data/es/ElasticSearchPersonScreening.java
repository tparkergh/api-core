package gov.ca.cwds.data.es;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonStaff;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.DomainChef;

/**
 * Screening.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonScreening extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  private String id;

  @JsonProperty("referral_id")
  private String referralId;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("end_date")
  private String endDate;

  @JsonProperty("county_name")
  private String countyName;

  @JsonProperty("decision")
  private String decision;

  @JsonProperty("response_time")
  private String responseTime;

  @JsonProperty("service_name")
  private String serviceName;

  @JsonProperty("category")
  private String category;

  @JsonProperty("reporter")
  private ElasticSearchPersonReporter reporter = new ElasticSearchPersonReporter();

  @JsonProperty("assigned_social_worker")
  private ElasticSearchPersonSocialWorker assignedSocialWorker =
      new ElasticSearchPersonSocialWorker();

  @JsonProperty("staff_name")
  private ElasticSearchPersonStaff staffName = new ElasticSearchPersonStaff();

  @JsonProperty("allegations")
  private List<ElasticSearchPersonAllegation> allegations = new ArrayList<>();

  @JsonProperty("all_people")
  private List<ElasticSearchPersonAny> allPeople = new ArrayList<>();

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public Date getStartDate() {
    return DomainChef.uncookDateString(startDate);
  }

  @SuppressWarnings("javadoc")
  public void setStartDate(Date startDate) {
    this.startDate = DomainChef.cookDate(startDate);
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public Date getEndDate() {
    return DomainChef.uncookDateString(endDate);
  }

  @SuppressWarnings("javadoc")
  public void setEndDate(Date endDate) {
    this.endDate = DomainChef.cookDate(endDate);
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public String getCountyName() {
    return countyName;
  }

  @SuppressWarnings("javadoc")
  public void setCountyName(String countyName) {
    this.countyName = countyName;
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public String getDecision() {
    return decision;
  }

  @SuppressWarnings("javadoc")
  public void setDecision(String decision) {
    this.decision = decision;
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public String getResponseTime() {
    return responseTime;
  }

  @SuppressWarnings("javadoc")
  public void setResponseTime(String responseTime) {
    this.responseTime = responseTime;
  }

  @SuppressWarnings("javadoc")
  @JsonIgnore
  public String getServiceName() {
    return serviceName;
  }

  @SuppressWarnings("javadoc")
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  @JsonIgnore
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @JsonIgnore
  public ElasticSearchPersonReporter getReporter() {
    return reporter;
  }

  public void setReporter(ElasticSearchPersonReporter reporter) {
    this.reporter = reporter;
  }

  @JsonIgnore
  public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  @JsonIgnore
  public ElasticSearchPersonStaff getStaffName() {
    return staffName;
  }

  public void setStaffName(ElasticSearchPersonStaff staffName) {
    this.staffName = staffName;
  }

  @JsonIgnore
  public List<ElasticSearchPersonAllegation> getAllegations() {
    return allegations;
  }

  public void setAllegations(List<ElasticSearchPersonAllegation> allegations) {
    this.allegations = allegations;
  }

  @JsonIgnore
  public List<ElasticSearchPersonAny> getAllPeople() {
    return allPeople;
  }

  public void setAllPeople(List<ElasticSearchPersonAny> allPeople) {
    this.allPeople = allPeople;
  }

  public String getReferralId() {
    return referralId;
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }
}
