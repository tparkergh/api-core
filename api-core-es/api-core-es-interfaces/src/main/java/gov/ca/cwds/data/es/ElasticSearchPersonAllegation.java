package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Screening allegation.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonAllegation extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("legacy_allegation_id")
  // @Deprecated
  private String legacyId;

  @JsonProperty("allegation_description")
  private String allegationDescription;

  @JsonProperty("disposition_description")
  private String dispositionDescription;

  @JsonProperty("disposition_id")
  private String dispositionId;

  @JsonProperty("perpetrator_id")
  // @Deprecated
  private String perpetratorId;

  @JsonProperty("perpetrator_first_name")
  // @Deprecated
  private String perpetratorFirstName;

  @JsonProperty("perpetrator_last_name")
  // @Deprecated
  private String perpetratorLastName;

  @JsonProperty("perpetrator_legacy_client_id")
  // @Deprecated
  private String perpetratorLegacyClientId;

  @JsonProperty("victim_id")
  // @Deprecated
  private String victimId;

  @JsonProperty("victim_first_name")
  // @Deprecated
  private String victimFirstName;

  @JsonProperty("victim_last_name")
  // @Deprecated
  private String victimLastName;

  @JsonProperty("victim_legacy_client_id")
  // @Deprecated
  private String victimLegacyClientId;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @JsonProperty("victim")
  private ElasticSearchPersonNestedPerson victim = new ElasticSearchPersonNestedPerson();

  @JsonProperty("perpetrator")
  private ElasticSearchPersonNestedPerson perpetrator = new ElasticSearchPersonNestedPerson();

  @SuppressWarnings("javadoc")
  public ElasticSearchPersonNestedPerson getVictim() {
    return victim;
  }

  @SuppressWarnings("javadoc")
  public void setVictim(ElasticSearchPersonNestedPerson victim) {
    this.victim = victim;
  }

  @SuppressWarnings("javadoc")
  public ElasticSearchPersonNestedPerson getPerpetrator() {
    return perpetrator;
  }

  @SuppressWarnings("javadoc")
  public void setPerpetrator(ElasticSearchPersonNestedPerson perpetrator) {
    this.perpetrator = perpetrator;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getLegacyId() {
    return legacyId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  @SuppressWarnings("javadoc")
  public String getAllegationDescription() {
    return allegationDescription;
  }

  @SuppressWarnings("javadoc")
  public void setAllegationDescription(String allegationDescription) {
    this.allegationDescription = allegationDescription;
  }

  @SuppressWarnings("javadoc")
  public String getDispositionDescription() {
    return dispositionDescription;
  }

  @SuppressWarnings("javadoc")
  public void setDispositionDescription(String dispositionDescription) {
    this.dispositionDescription = dispositionDescription;
  }

  @SuppressWarnings("javadoc")
  public String getDispositionId() {
    return dispositionId;
  }

  @SuppressWarnings("javadoc")
  public void setDispositionId(String dispositionId) {
    this.dispositionId = dispositionId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getPerpetratorId() {
    return perpetratorId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setPerpetratorId(String perpetratorId) {
    this.perpetratorId = perpetratorId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getPerpetratorFirstName() {
    return perpetratorFirstName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setPerpetratorFirstName(String perpetratorFirstName) {
    this.perpetratorFirstName = perpetratorFirstName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getPerpetratorLastName() {
    return perpetratorLastName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setPerpetratorLastName(String perpetratorLastName) {
    this.perpetratorLastName = perpetratorLastName;
  }

  // @Deprecated
  public String getPerpetratorLegacyClientId() {
    return perpetratorLegacyClientId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setPerpetratorLegacyClientId(String perpetratorLegacyClientId) {
    this.perpetratorLegacyClientId = perpetratorLegacyClientId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getVictimId() {
    return victimId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setVictimId(String victimId) {
    this.victimId = victimId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getVictimFirstName() {
    return victimFirstName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setVictimFirstName(String victimFirstName) {
    this.victimFirstName = victimFirstName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getVictimLastName() {
    return victimLastName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setVictimLastName(String victimLastName) {
    this.victimLastName = victimLastName;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public String getVictimLegacyClientId() {
    return victimLegacyClientId;
  }

  // @Deprecated
  @SuppressWarnings("javadoc")
  public void setVictimLegacyClientId(String victimLegacyClientId) {
    this.victimLegacyClientId = victimLegacyClientId;
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
