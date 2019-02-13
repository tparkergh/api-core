package gov.ca.cwds.cms.data.access.mapper;

import gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Gender;
import gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin;
import gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** @author CWDS TPT-3 Team */
@Mapper(
  imports = {
    DateOfBirthStatus.class,
    AdoptionStatus.class,
    HispanicOrigin.class,
    Soc158placementsStatus.class,
    UnableToDetermineReason.class,
    MilitaryStatus.class,
    LiterateStatus.class,
    IncapacitatedParentStatus.class,
    Gender.class,
    Sensitivity.class,
    CmsKeyIdGenerator.class,
    ParentUnemployedStatus.class
  },
  collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
@SuppressWarnings("Duplicates")
@FunctionalInterface
public interface ClientMapper {

  @Mapping(source = "adjudicatedDelinquentIndicator", target = "adjudicatedDelinquentIndicator")
  @Mapping(source = "id", target = "identifier")
  @Mapping(
    expression = "java(AdoptionStatus.fromCode(client.getAdoptionStatusCode()))",
    target = "adoptionStatus"
  )
  @Mapping(source = "alienRegistrationNumber", target = "alienRegistrationNumber")
  @Mapping(source = "birthCity", target = "birthCity")
  @Mapping(source = "birthCountryCodeType", target = "birthCountryCode")
  @Mapping(source = "birthDate", target = "birthDate")
  @Mapping(source = "birthFacilityName", target = "birthFacilityName")
  @Mapping(source = "birthStateCodeType", target = "birthStateCode")
  @Mapping(source = "birthplaceVerifiedIndicator", target = "birthplaceVerifiedIndicator")
  @Mapping(source = "childClientIndicatorVar", target = "childClientIndicator")
  @Mapping(source = "clientIndexNumber", target = "clientIndexNumber")
  @Mapping(source = "commentDescription", target = "commentDescription")
  @Mapping(source = "commonFirstName", target = "commonFirstName")
  @Mapping(source = "commonMiddleName", target = "commonMiddleName")
  @Mapping(source = "commonLastName", target = "commonLastName")
  @Mapping(source = "confidentialityActionDate", target = "confidentialityActionDate")
  @Mapping(source = "confidentialityInEffectIndicator", target = "confidentialityInEffectIndicator")
  @Mapping(source = "creationDate", target = "creationDate") // converter
  @Mapping(source = "currCaChildrenServIndicator", target = "currentCaChildrenServiceIndicator")
  @Mapping(source = "currentlyOtherDescription", target = "currentlyOtherDescription")
  @Mapping(source = "currentlyRegionalCenterIndicator", target = "currentlyRegionalCenterIndicator")
  @Mapping(source = "deathDate", target = "deathDate") // converter
  @Mapping(source = "deathDateVerifiedIndicator", target = "deathDateVerifiedIndicator")
  @Mapping(source = "deathPlace", target = "deathPlace")
  @Mapping(source = "deathReasonText", target = "deathReasonText")
  @Mapping(source = "driverLicenseNumber", target = "driverLicenseNumber")
  @Mapping(source = "driverLicenseStateCodeType", target = "driverLicenseStateCode")
  @Mapping(source = "emailAddress", target = "emailAddress")
  @Mapping(
    expression = "java(DateOfBirthStatus.fromCode(client.getEstimatedDobCode()))",
    target = "dateOfBirthStatus"
  )
  @Mapping(
    expression = "java(UnableToDetermineReason.fromCode(client.getEthUnableToDetReasonCode()))",
    target = "ethnicityUnableToDetermineReason"
  )
  @Mapping(source = "fatherParentalRightTermDate", target = "fatherParentalRightTermDate")
  @Mapping(expression = "java(Gender.fromCode(client.getGenderCode()))", target = "gender")
  @Mapping(source = "genderIdentityType", target = "genderIdentityCode")
  @Mapping(source = "giNotListedDescription", target = "genderIdentityNotListedDescription")
  @Mapping(source = "genderExpressionType", target = "genderEspressionCode")
  @Mapping(source = "healthSummaryText", target = "healthSummaryText")
  @Mapping(
    expression = "java(UnableToDetermineReason.fromCode(client.getHispUnableToDetReasonCode()))",
    target = "hispanicUnableToDetermineReason"
  )
  @Mapping(
    expression = "java(HispanicOrigin.fromCode(client.getHispanicOriginCode()))",
    target = "hispanicOrigin"
  )
  @Mapping(source = "immigrationCountryCodeType", target = "immigrationCountryCode")
  @Mapping(source = "immigrationStatusType", target = "immigrationStatusCode")
  @Mapping(
    expression = "java(IncapacitatedParentStatus.fromCode(client.getIncapacitatedParentCode()))",
    target = "incapacitatedParentStatus"
  )
  @Mapping(
    source = "individualHealthCarePlanIndicator",
    target = "individualHealthCarePlanIndicator"
  )
  @Mapping(source = "limitationOnScpHealthIndicator", target = "limitationOnScpHealthIndicator")
  @Mapping(
    expression = "java(LiterateStatus.fromCode(client.getLiterateCode()))",
    target = "literateStatus"
  )
  @Mapping(
    source = "maritalCohabitatnHstryIndicatorVar",
    target = "maritalCohabitationHistoryIndicator"
  )
  @Mapping(source = "maritalStatusType", target = "maritalStatusCode")
  @Mapping(
    expression = "java(MilitaryStatus.fromCode(client.getMilitaryStatusCode()))",
    target = "militaryStatus"
  )
  @Mapping(source = "motherParentalRightTermDate", target = "motherParentalRightTermDate")
  @Mapping(source = "namePrefixDescription", target = "namePrefixDescription")
  @Mapping(source = "nameType", target = "nameType.systemId")
  @Mapping(source = "outstandingWarrantIndicator", target = "outstandingWarrantIndicator")
  @Mapping(source = "prevCaChildrenServIndicator", target = "previousCaChildrenServiceIndicator")
  @Mapping(source = "prevOtherDescription", target = "previousOtherDescription")
  @Mapping(source = "prevRegionalCenterIndicator", target = "previousRegionalCenterIndicator")
  @Mapping(source = "primaryEthnicityType", target = "primaryEthnicityCode")
  @Mapping(source = "primaryLanguageType", target = "primaryLanguageCode")
  @Mapping(source = "religionType", target = "religionCode")
  @Mapping(source = "secondaryLanguageType", target = "secondaryLanguageCode")
  @Mapping(
    source = "sensitiveHlthInfoOnFileIndicator",
    target = "sensitiveHealthInfoOnFileIndicator"
  )
  @Mapping(
    expression = "java(Sensitivity.fromCode(client.getSensitivityIndicator()))",
    target = "sensitivity"
  )
  @Mapping(source = "sexualOrientationType", target = "sexualOrientationCode")
  @Mapping(source = "soUnableToDetermineCode", target = "sexualOrientationUnableToDetermineCode")
  @Mapping(source = "soNotListedDescrption", target = "sexualOrientationNotListedDescription")
  @Mapping(
    expression = "java(Soc158placementsStatus.fromCode(client.getSoc158PlacementCode()))",
    target = "soc158placementsStatus"
  )
  @Mapping(source = "soc158SealedClientIndicator", target = "soc158SealedClientIndicator")
  @Mapping(source = "socialSecurityNumChangedCode", target = "socialSecurityNumberChangedCode")
  @Mapping(source = "socialSecurityNumber", target = "socialSecurityNumber")
  @Mapping(source = "suffixTitleDescription", target = "suffixTitleDescription")
  @Mapping(source = "tribalAncestryClientIndicatorVar", target = "tribalAncestryClientIndicator")
  @Mapping(
    source = "tribalMembrshpVerifctnIndicatorVar",
    target = "tribalMembershipVerifcationIndicator"
  )
  @Mapping(
    expression = "java(ParentUnemployedStatus.fromCode(client.getUnemployedParentCode()))",
    target = "parentUnemployedStatus"
  )
  @Mapping(source = "zippyCreatedIndicator", target = "zippyCreatedIndicator")
  @Mapping(target = "lastUpdateId", ignore = true)
  @Mapping(target = "lastUpdateTime", ignore = true)
  gov.ca.cwds.data.legacy.cms.entity.Client toLegacyClient(BaseClient client);
}
