package gov.ca.cwds.data.es;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Case.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonCase extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {
  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("legacy_case_id")
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

  @JsonProperty("service_component")
  private String serviceComponent;

  @JsonProperty("service_component_id")
  private String serviceComponentId;

  @JsonProperty("focus_child")
  private ElasticSearchPersonChild focusChild;

  @JsonProperty("assigned_social_worker")
  private ElasticSearchPersonSocialWorker assignedSocialWorker =
      new ElasticSearchPersonSocialWorker();

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("parents")
  private List<ElasticSearchPersonParent> parents = new ArrayList<>();

  @JsonProperty("access_limitation")
  private ElasticSearchAccessLimitation accessLimitation = new ElasticSearchAccessLimitation();

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

  public String getServiceComponent() {
    return serviceComponent;
  }

  public void setServiceComponent(String serviceComponent) {
    this.serviceComponent = serviceComponent;
  }

  public ElasticSearchPersonChild getFocusChild() {
    return focusChild;
  }

  public void setFocusChild(ElasticSearchPersonChild focusChild) {
    this.focusChild = focusChild;
  }

  public ElasticSearchPersonSocialWorker getAssignedSocialWorker() {
    return assignedSocialWorker;
  }

  public void setAssignedSocialWorker(ElasticSearchPersonSocialWorker assignedSocialWorker) {
    this.assignedSocialWorker = assignedSocialWorker;
  }

  public List<ElasticSearchPersonParent> getParents() {
    return parents;
  }

  public void setParents(List<ElasticSearchPersonParent> parents) {
    this.parents = parents;
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

  public String getServiceComponentId() {
    return serviceComponentId;
  }

  public void setServiceComponentId(String serviceComponentId) {
    this.serviceComponentId = serviceComponentId;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

}
