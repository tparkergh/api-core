package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.converter.NullableBooleanConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

/**
 * @author CWDS CALS API Team
 */
@NamedQuery(
    name = "Client.find",
    query = "SELECT c FROM Client c"
        + " JOIN c.placementEpisodes pe"
        + " JOIN pe.outOfHomePlacements ohp"
        + " JOIN ohp.placementHome ph"
        + " WHERE ph.licenseNo = :licenseNumber AND c.identifier = :childId"
)
@NamedQuery(
    name = "Client.findAll",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.licenseNo = :licenseNumber" +
        " ORDER BY c.identifier "
)
@NamedQuery(
    name = "Client.findByFacilityId",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.id = :facilityId"
)
@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CLIENT_T")
public class Client extends CmsPersistentObject implements IClient, PersistentObject {

  private static final long serialVersionUID = 783532074047017463L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
  @OrderBy("removalDt DESC")
  private Set<PlacementEpisode> placementEpisodes = new HashSet<>();

  @Column(name = "ADJDEL_IND", length = 1)
  @Convert(converter = NullableBooleanConverter.class)
  private Boolean adjudicatedDelinquentIndicator;

  @Column(name = "ADPTN_STCD", nullable = false, length = 1)
  @Convert(converter = AdoptionStatus.AdoptionStatusConverter.class)
  private AdoptionStatus adoptionStatus;

  @Column(name = "ALN_REG_NO", nullable = false, length = 12)
  @ColumnTransformer(read = "trim(ALN_REG_NO)")
  private String alienRegistrationNumber;

  @Column(name = "B_CNTRY_C", nullable = false)
  private Short birthCountryCodeType;

  @Column(name = "B_STATE_C", nullable = false)
  private Short birthStateCodeType;

  @Column(name = "BIRTH_CITY", nullable = false, length = 35)
  @ColumnTransformer(read = "trim(BIRTH_CITY)")
  private String birthCity;

  @Column(name = "BIRTH_DT")
  private LocalDate birthDate;

  @Type(type = "yes_no")
  @Column(name = "BP_VER_IND", nullable = false, length = 1)
  private boolean birthplaceVerifiedIndicator;

  @Column(name = "BR_FAC_NM", nullable = false, length = 35)
  @ColumnTransformer(read = "trim(BR_FAC_NM)")
  private String birthFacilityName;

  @Type(type = "yes_no")
  @Column(name = "CHLD_CLT_B", nullable = false, length = 1)
  private boolean childClientIndicator;

  @Column(name = "COM_FST_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(COM_FST_NM)")
  private String commonFirstName;

  @Column(name = "COM_LST_NM", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(COM_LST_NM)")
  private String commonLastName;

  @Column(name = "COM_MID_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(COM_MID_NM)")
  private String commonMiddleName;

  @Column(name = "COMMNT_DSC", nullable = false, length = 120)
  @ColumnTransformer(read = "trim(COMMNT_DSC)")
  private String commentDescription;

  @Column(name = "CONF_ACTDT")
  private LocalDate confidentialityActionDate;

  @Type(type = "yes_no")
  @Column(name = "CONF_EFIND", nullable = false, length = 1)
  private boolean confidentialityInEffectIndicator;

  @Column(name = "COTH_DESC", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(COTH_DESC)")
  private String currentlyOtherDescription;

  @Column(name = "CREATN_DT", nullable = false)
  private LocalDate creationDate;

  @Type(type = "yes_no")
  @Column(name = "CURRCA_IND", nullable = false, length = 1)
  private boolean currentCaChildrenServiceIndicator;

  @Type(type = "yes_no")
  @Column(name = "CURREG_IND", nullable = false, length = 1)
  private boolean currentlyRegionalCenterIndicator;

  @Column(name = "D_STATE_C", nullable = false)
  private Short driverLicenseStateCodeType;

  @Column(name = "DEATH_DT")
  private LocalDate deathDate;

  @Column(name = "DEATH_PLC", length = 35)
  @ColumnTransformer(read = "trim(DEATH_PLC)")
  private String deathPlace;

  @Column(name = "DRV_LIC_NO", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(DRV_LIC_NO)")
  private String driverLicenseNumber;

  @Type(type = "yes_no")
  @Column(name = "DTH_DT_IND", nullable = false, length = 1)
  private boolean deathDateVerifiedIndicator;

  @Column(name = "DTH_RN_TXT", length = 10)
  @ColumnTransformer(read = "trim(DTH_RN_TXT)")
  private String deathReasonText;

  @Column(name = "EMAIL_ADDR", length = 50)
  @ColumnTransformer(read = "trim(EMAIL_ADDR)")
  private String emailAddress;

  @Column(name = "EST_DOB_CD", nullable = false, length = 1)
  private String estimatedDobCode;

  @Column(name = "ETH_UD_CD", length = 1)
  private String ethnicityUnableToDetermineReasonCode;

  @Column(name = "FTERM_DT")
  private LocalDate fatherParentalRightTermDate;

  @Column(name = "CL_INDX_NO", length = 12)
  @ColumnTransformer(read = "trim(CL_INDX_NO)")
  private String clientIndexNumber;

  @Column(name = "GENDER_CD", nullable = false, length = 1)
  private String genderCode;

  @Type(type = "yes_no")
  @Column(name = "HCARE_IND", nullable = false, length = 1)
  private boolean individualHealthCarePlanIndicator;

  @Column(name = "HEALTH_TXT", length = 10)
  @ColumnTransformer(read = "trim(HEALTH_TXT)")
  private String healthSummaryText;

  @Column(name = "HISP_CD", nullable = false, length = 1)
  private String hispanicOriginCode;

  @Column(name = "HISP_UD_CD", length = 1)
  private String hispanicUnableToDetermineReasonCode;

  @Column(name = "I_CNTRY_C", nullable = false)
  private Short immigrationCountryCodeType;

  @Column(name = "IMGT_STC", nullable = false)
  private Short immigrationStatusType;

  @Column(name = "INCAPC_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(INCAPC_CD)")
  private String incapacitatedParentCode;

  @Type(type = "yes_no")
  @Column(name = "LIMIT_IND", nullable = false, length = 1)
  private boolean limitationOnScpHealthIndicator;

  @Column(name = "LITRATE_CD", nullable = false, length = 1)
  @ColumnTransformer(read = "trim(LITRATE_CD)")
  private String literateCode;

  @Type(type = "yes_no")
  @Column(name = "MAR_HIST_B", nullable = false, length = 1)
  private boolean maritalCohabitationHistoryIndicator;

  @Column(name = "MILT_STACD", nullable = false, length = 1)
  private String militaryStatusCode;

  @Column(name = "MRTL_STC", nullable = false)
  private Short maritalStatusType;

  @Column(name = "MTERM_DT")
  private LocalDate motherParentalRightTermDate;

  @Column(name = "NAME_TPC", nullable = false)
  private Short nameType;

  @Column(name = "NMPRFX_DSC", nullable = false, length = 6)
  @ColumnTransformer(read = "trim(NMPRFX_DSC)")
  private String namePrefixDescription;

  @Type(type = "yes_no")
  @Column(name = "OUTWRT_IND", nullable = false, length = 1)
  private boolean outstandingWarrantIndicator;

  @Column(name = "P_ETHNCTYC", nullable = false)
  private Short primaryEthnicityType;

  @Column(name = "P_LANG_TPC", nullable = false)
  private Short primaryLanguageType;

  @Column(name = "POTH_DESC", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(POTH_DESC)")
  private String previousOtherDescription;

  @Type(type = "yes_no")
  @Column(name = "PREREG_IND", nullable = false, length = 1)
  private boolean previousRegionalCenterIndicator;

  @Type(type = "yes_no")
  @Column(name = "PREVCA_IND", nullable = false, length = 1)
  private boolean previousCaChildrenServiceIndicator;

  @Column(name = "RLGN_TPC", nullable = false)
  private Short religionType;

  @Column(name = "S_LANG_TC", nullable = false)
  private Short secondaryLanguageType;

  @Column(name = "SENSTV_IND", nullable = false, length = 1)
  private String sensitivityIndicator;

  @Type(type = "yes_no")
  @Column(name = "SNTV_HLIND", nullable = false, length = 1)
  private boolean sensitiveHealthInfoOnFileIndicator;

  @Type(type = "yes_no")
  @Column(name = "SOC158_IND", nullable = false, length = 1)
  private boolean soc158SealedClientIndicator;

  @Column(name = "SOCPLC_CD", nullable = false, length = 1)
  private String soc158PlacementCode;

  @Column(name = "SS_NO", nullable = false, length = 9)
  @ColumnTransformer(read = "trim(SS_NO)")
  private String socialSecurityNumber;

  @Column(name = "SSN_CHG_CD", nullable = false, length = 1)
  private String socialSecurityNumberChangedCode;

  @Column(name = "SUFX_TLDSC", nullable = false, length = 4)
  @ColumnTransformer(read = "trim(SUFX_TLDSC)")
  private String suffixTitleDescription;

  @Type(type = "yes_no")
  @Column(name = "TR_MBVRT_B", nullable = false, length = 1)
  private boolean tribalMembershipVerifcationIndicator;

  @Type(type = "yes_no")
  @Column(name = "TRBA_CLT_B", nullable = false, length = 1)
  private boolean tribalAncestryClientIndicator;

  @Column(name = "UNEMPLY_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(UNEMPLY_CD)")
  private String unemployedParentCode;

  @Type(type = "yes_no")
  @Column(name = "ZIPPY_IND", nullable = false, length = 1)
  private boolean zippyCreatedIndicator;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public AdoptionStatus getAdoptionStatus() {
    return adoptionStatus;
  }

  public void setAdoptionStatus(AdoptionStatus adoptionStatus) {
    this.adoptionStatus = adoptionStatus;
  }

  public String getAlienRegistrationNumber() {
    return alienRegistrationNumber;
  }

  public void setAlienRegistrationNumber(String alienRegistrationNumber) {
    this.alienRegistrationNumber = alienRegistrationNumber;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }


  public String getBirthFacilityName() {
    return birthFacilityName;
  }

  public void setBirthFacilityName(String birthFacilityName) {
    this.birthFacilityName = birthFacilityName;
  }

  public Short getBirthStateCodeType() {
    return birthStateCodeType;
  }

  public void setBirthStateCodeType(Short birthStateCodeType) {
    this.birthStateCodeType = birthStateCodeType;
  }


  public Short getBirthCountryCodeType() {
    return birthCountryCodeType;
  }

  public void setBirthCountryCodeType(Short birthCountryCodeType) {
    this.birthCountryCodeType = birthCountryCodeType;
  }

  public boolean getChildClientIndicator() {
    return childClientIndicator;
  }

  public void setChildClientIndicator(boolean childClientIndicator) {
    this.childClientIndicator = childClientIndicator;
  }

  public String getCommonFirstName() {
    return commonFirstName;
  }

  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }


  public String getCommonLastName() {
    return commonLastName;
  }

  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }

  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
  }

  public boolean getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  public void setConfidentialityInEffectIndicator(boolean confidentialityInEffectIndicator) {
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
  }

  public LocalDate getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  public void setConfidentialityActionDate(LocalDate confidentialityActionDate) {
    this.confidentialityActionDate = confidentialityActionDate;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getDeathDate() {
    return deathDate;
  }

  public void setDeathDate(LocalDate deathDate) {
    this.deathDate = deathDate;
  }


  public String getDeathReasonText() {
    return deathReasonText;
  }

  public void setDeathReasonText(String deathReasonText) {
    this.deathReasonText = deathReasonText;
  }

  public String getDriverLicenseNumber() {
    return driverLicenseNumber;
  }

  public void setDriverLicenseNumber(String driverLicenseNumber) {
    this.driverLicenseNumber = driverLicenseNumber;
  }

  public Short getDriverLicenseStateCodeType() {
    return driverLicenseStateCodeType;
  }

  public void setDriverLicenseStateCodeType(Short driverLicenseStateCodeType) {
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
  }

  public String getGenderCode() {
    return genderCode;
  }

  public void setGenderCode(String genderCode) {
    this.genderCode = genderCode;
  }

  public Short getImmigrationCountryCodeType() {
    return immigrationCountryCodeType;
  }

  public void setImmigrationCountryCodeType(Short immigrationCountryCodeType) {
    this.immigrationCountryCodeType = immigrationCountryCodeType;
  }

  public Short getImmigrationStatusType() {
    return immigrationStatusType;
  }

  public void setImmigrationStatusType(Short immigrationStatusType) {
    this.immigrationStatusType = immigrationStatusType;
  }

  public String getIncapacitatedParentCode() {
    return incapacitatedParentCode;
  }

  public void setIncapacitatedParentCode(String incapacitatedParentCode) {
    this.incapacitatedParentCode = incapacitatedParentCode;
  }

  public String getLiterateCode() {
    return literateCode;
  }

  public void setLiterateCode(String literateCode) {
    this.literateCode = literateCode;
  }

  public boolean getMaritalCohabitationHistoryIndicator() {
    return maritalCohabitationHistoryIndicator;
  }

  public void setMaritalCohabitationHistoryIndicator(boolean maritalCohabitationHistoryIndicator) {
    this.maritalCohabitationHistoryIndicator = maritalCohabitationHistoryIndicator;
  }

  public Short getMaritalStatusType() {
    return maritalStatusType;
  }

  public void setMaritalStatusType(Short maritalStatusType) {
    this.maritalStatusType = maritalStatusType;
  }

  public String getMilitaryStatusCode() {
    return militaryStatusCode;
  }

  public void setMilitaryStatusCode(String militaryStatusCode) {
    this.militaryStatusCode = militaryStatusCode;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public void setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
  }

  public Short getNameType() {
    return nameType;
  }

  public void setNameType(Short nameType) {
    this.nameType = nameType;
  }

  public boolean getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  public void setOutstandingWarrantIndicator(boolean outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
  }

  public Short getPrimaryEthnicityType() {
    return primaryEthnicityType;
  }

  public void setPrimaryEthnicityType(Short primaryEthnicityType) {
    this.primaryEthnicityType = primaryEthnicityType;
  }

  public Short getPrimaryLanguageType() {
    return primaryLanguageType;
  }

  public void setPrimaryLanguageType(Short primaryLanguageType) {
    this.primaryLanguageType = primaryLanguageType;
  }

  public Short getReligionType() {
    return religionType;
  }

  public void setReligionType(Short religionType) {
    this.religionType = religionType;
  }

  public Short getSecondaryLanguageType() {
    return secondaryLanguageType;
  }

  public void setSecondaryLanguageType(Short secondaryLanguageType) {
    this.secondaryLanguageType = secondaryLanguageType;
  }

  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  public void setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
  }

  public boolean getSensitiveHealthInfoOnFileIndicator() {
    return sensitiveHealthInfoOnFileIndicator;
  }

  public void setSensitiveHealthInfoOnFileIndicator(boolean sensitiveHealthInfoOnFileIndicator) {
    this.sensitiveHealthInfoOnFileIndicator = sensitiveHealthInfoOnFileIndicator;
  }

  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public void setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }

  public String getSocialSecurityNumberChangedCode() {
    return socialSecurityNumberChangedCode;
  }

  public void setSocialSecurityNumberChangedCode(String socialSecurityNumberChangedCode) {
    this.socialSecurityNumberChangedCode = socialSecurityNumberChangedCode;
  }

  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public void setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
  }

  public String getUnemployedParentCode() {
    return unemployedParentCode;
  }

  public void setUnemployedParentCode(String unemployedParentCode) {
    this.unemployedParentCode = unemployedParentCode;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }

  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  public void setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
  }

  public boolean getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  public void setBirthplaceVerifiedIndicator(boolean birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public void setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
  }

  public boolean getCurrentCaChildrenServiceIndicator() {
    return currentCaChildrenServiceIndicator;
  }

  public void setCurrentCaChildrenServiceIndicator(boolean currentCaChildrenServiceIndicator) {
    this.currentCaChildrenServiceIndicator = currentCaChildrenServiceIndicator;
  }

  public boolean getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  public void setCurrentlyRegionalCenterIndicator(boolean currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
  }

  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  public void setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
  }

  public boolean getPreviousCaChildrenServiceIndicator() {
    return previousCaChildrenServiceIndicator;
  }

  public void setPreviousCaChildrenServiceIndicator(boolean previousCaChildrenServiceIndicator) {
    this.previousCaChildrenServiceIndicator = previousCaChildrenServiceIndicator;
  }

  public boolean getPreviousRegionalCenterIndicator() {
    return previousRegionalCenterIndicator;
  }

  public void setPreviousRegionalCenterIndicator(boolean previousRegionalCenterIndicator) {
    this.previousRegionalCenterIndicator = previousRegionalCenterIndicator;
  }

  public String getPreviousOtherDescription() {
    return previousOtherDescription;
  }

  public void setPreviousOtherDescription(String previousOtherDescription) {
    this.previousOtherDescription = previousOtherDescription;
  }

  public boolean getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  public void setIndividualHealthCarePlanIndicator(boolean individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
  }

  public boolean getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  public void setLimitationOnScpHealthIndicator(boolean limitationOnScpHealthIndicator) {
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }


  public String getHealthSummaryText() {
    return healthSummaryText;
  }

  public void setHealthSummaryText(String healthSummaryText) {
    this.healthSummaryText = healthSummaryText;
  }

  public LocalDate getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
  }

  public void setMotherParentalRightTermDate(LocalDate motherParentalRightTermDate) {
    this.motherParentalRightTermDate = motherParentalRightTermDate;
  }

  public LocalDate getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  public void setFatherParentalRightTermDate(LocalDate fatherParentalRightTermDate) {
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
  }

  public boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public void setZippyCreatedIndicator(boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public String getDeathPlace() {
    return deathPlace;
  }

  public void setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
  }

  public boolean getTribalMembershipVerifcationIndicator() {
    return tribalMembershipVerifcationIndicator;
  }

  public void setTribalMembershipVerifcationIndicator(
      boolean tribalMembershipVerifcationIndicator) {
    this.tribalMembershipVerifcationIndicator = tribalMembershipVerifcationIndicator;
  }

  public boolean getTribalAncestryClientIndicator() {
    return tribalAncestryClientIndicator;
  }

  public void setTribalAncestryClientIndicator(boolean tribalAncestryClientIndicator) {
    this.tribalAncestryClientIndicator = tribalAncestryClientIndicator;
  }

  public boolean getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  public void setSoc158SealedClientIndicator(boolean soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
  }

  public boolean getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  public void setDeathDateVerifiedIndicator(boolean deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @return adjudicatedDelinquentIndicator (Boolean value or null)
   */
  @SuppressWarnings("squid:S2447")
  public Boolean getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  /**
   *
   * @param adjudicatedDelinquentIndicator (may be null)
   */
  public void setAdjudicatedDelinquentIndicator(Boolean adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
  }

  public String getEthnicityUnableToDetermineReasonCode() {
    return ethnicityUnableToDetermineReasonCode;
  }

  public void setEthnicityUnableToDetermineReasonCode(String ethnicityUnableToDetermineReasonCode) {
    this.ethnicityUnableToDetermineReasonCode = ethnicityUnableToDetermineReasonCode;
  }

  public String getHispanicUnableToDetermineReasonCode() {
    return hispanicUnableToDetermineReasonCode;
  }

  public void setHispanicUnableToDetermineReasonCode(String hispanicUnableToDetermineReasonCode) {
    this.hispanicUnableToDetermineReasonCode = hispanicUnableToDetermineReasonCode;
  }

  public String getSoc158PlacementCode() {
    return soc158PlacementCode;
  }

  public void setSoc158PlacementCode(String soc158PlacementCode) {
    this.soc158PlacementCode = soc158PlacementCode;
  }

  public String getClientIndexNumber() {
    return clientIndexNumber;
  }

  public void setClientIndexNumber(String clientIndexNumber) {
    this.clientIndexNumber = clientIndexNumber;
  }

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }

  @Override
  public Set<PlacementEpisode> getPlacementEpisodes() {
    return placementEpisodes;
  }

  public void setPlacementEpisodes(Set<PlacementEpisode> placementEpisodes) {
    this.placementEpisodes = placementEpisodes;
  }
}
