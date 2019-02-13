package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.data.legacy.cms.entity.PhoneContactDetail;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class SCPEntityAwareDTO extends BaseEntityAwareDTO<SubstituteCareProvider> {

  private String placementHomeId;
  private boolean primaryApplicant;
  private List<CWSIdentifier> ethnicityList = new ArrayList<>();
  private List<? extends CWSIdentifier> otherStatesOfLiving = new ArrayList<>();
  private List<PhoneContactDetail> phoneNumbers = new ArrayList<>();

  public String getPlacementHomeId() {
    return placementHomeId;
  }

  public void setPlacementHomeId(String placementHomeId) {
    this.placementHomeId = placementHomeId;
  }

  public boolean isPrimaryApplicant() {
    return primaryApplicant;
  }

  public void setPrimaryApplicant(boolean primaryApplicant) {
    this.primaryApplicant = primaryApplicant;
  }

  public List<PhoneContactDetail> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(
      List<PhoneContactDetail> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public List<CWSIdentifier> getEthnicityList() {
    return ethnicityList;
  }

  public void setEthnicityList(
      List<CWSIdentifier> ethnicityList) {
    this.ethnicityList = ethnicityList;
  }

  @SuppressWarnings("squid:S1452")
  public List<? extends CWSIdentifier> getOtherStatesOfLiving() {
    return otherStatesOfLiving;
  }

  public void setOtherStatesOfLiving(
      List<? extends CWSIdentifier> otherStatesOfLiving) {
    this.otherStatesOfLiving = otherStatesOfLiving;
  }

}
