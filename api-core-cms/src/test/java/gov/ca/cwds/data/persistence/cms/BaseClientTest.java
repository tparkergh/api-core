package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import gov.ca.cwds.common.CaresTestHarness;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.std.ApiLanguageAware;

public class BaseClientTest extends CaresTestHarness<Client> {

  BaseClient target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new TestOnlyClient();
    target = new TestOnlyClient("0X5");
    target = new TestOnlyClient("0X5", new Date());
  }

  @Test
  public void type() throws Exception {
    assertThat(BaseClient.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdjudicatedDelinquentIndicator_Args__() throws Exception {
    String actual = target.getAdjudicatedDelinquentIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdoptionStatusCode_Args__() throws Exception {
    String actual = target.getAdoptionStatusCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAlienRegistrationNumber_Args__() throws Exception {
    String actual = target.getAlienRegistrationNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthCity_Args__() throws Exception {
    String actual = target.getBirthCity();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthCountryCodeType_Args__() throws Exception {
    Short actual = target.getBirthCountryCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthFacilityName_Args__() throws Exception {
    String actual = target.getBirthFacilityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthStateCodeType_Args__() throws Exception {
    Short actual = target.getBirthStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthplaceVerifiedIndicator_Args__() throws Exception {
    String actual = target.getBirthplaceVerifiedIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getChildClientIndicatorVar_Args__() throws Exception {
    String actual = target.getChildClientIndicatorVar();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getClientIndexNumber_Args__() throws Exception {
    String actual = target.getClientIndexNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommentDescription_Args__() throws Exception {
    String actual = target.getCommentDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommonFirstName_Args__() throws Exception {
    String actual = target.getCommonFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommonLastName_Args__() throws Exception {
    String actual = target.getCommonLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommonMiddleName_Args__() throws Exception {
    String actual = target.getCommonMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getConfidentialityActionDate_Args__() throws Exception {
    Date actual = target.getConfidentialityActionDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getConfidentialityInEffectIndicator_Args__() throws Exception {
    String actual = target.getConfidentialityInEffectIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCreationDate_Args__() throws Exception {
    Date actual = target.getCreationDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCurrCaChildrenServIndicator_Args__() throws Exception {
    String actual = target.getCurrCaChildrenServIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCurrentlyOtherDescription_Args__() throws Exception {
    String actual = target.getCurrentlyOtherDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCurrentlyRegionalCenterIndicator_Args__() throws Exception {
    String actual = target.getCurrentlyRegionalCenterIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeathDate_Args__() throws Exception {
    Date actual = target.getDeathDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeathDateVerifiedIndicator_Args__() throws Exception {
    String actual = target.getDeathDateVerifiedIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeathPlace_Args__() throws Exception {
    String actual = target.getDeathPlace();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDeathReasonText_Args__() throws Exception {
    String actual = target.getDeathReasonText();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDriverLicenseNumber_Args__() throws Exception {
    String actual = target.getDriverLicenseNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDriverLicenseStateCodeType_Args__() throws Exception {
    Short actual = target.getDriverLicenseStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEstimatedDobCode_Args__() throws Exception {
    String actual = target.getEstimatedDobCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEthUnableToDetReasonCode_Args__() throws Exception {
    String actual = target.getEthUnableToDetReasonCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFatherParentalRightTermDate_Args__() throws Exception {
    Date actual = target.getFatherParentalRightTermDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGenderCode_Args__() throws Exception {
    String actual = target.getGenderCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHealthSummaryText_Args__() throws Exception {
    String actual = target.getHealthSummaryText();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHispUnableToDetReasonCode_Args__() throws Exception {
    String actual = target.getHispUnableToDetReasonCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHispanicOriginCode_Args__() throws Exception {
    String actual = target.getHispanicOriginCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getImmigrationCountryCodeType_Args__() throws Exception {
    Short actual = target.getImmigrationCountryCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getImmigrationStatusType_Args__() throws Exception {
    Short actual = target.getImmigrationStatusType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIncapacitatedParentCode_Args__() throws Exception {
    String actual = target.getIncapacitatedParentCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIndividualHealthCarePlanIndicator_Args__() throws Exception {
    String actual = target.getIndividualHealthCarePlanIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitationOnScpHealthIndicator_Args__() throws Exception {
    String actual = target.getLimitationOnScpHealthIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLiterateCode_Args__() throws Exception {
    String actual = target.getLiterateCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaritalCohabitatnHstryIndicatorVar_Args__() throws Exception {
    String actual = target.getMaritalCohabitatnHstryIndicatorVar();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaritalStatusType_Args__() throws Exception {
    Short actual = target.getMaritalStatusType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMilitaryStatusCode_Args__() throws Exception {
    String actual = target.getMilitaryStatusCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMotherParentalRightTermDate_Args__() throws Exception {
    Date actual = target.getMotherParentalRightTermDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameType_Args__() throws Exception {
    Short actual = target.getNameType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOutstandingWarrantIndicator_Args__() throws Exception {
    String actual = target.getOutstandingWarrantIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrevCaChildrenServIndicator_Args__() throws Exception {
    String actual = target.getPrevCaChildrenServIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrevOtherDescription_Args__() throws Exception {
    String actual = target.getPrevOtherDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrevRegionalCenterIndicator_Args__() throws Exception {
    String actual = target.getPrevRegionalCenterIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryEthnicityType_Args__() throws Exception {
    Short actual = target.getPrimaryEthnicityType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryLanguageType_Args__() throws Exception {
    Short actual = target.getPrimaryLanguageType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReligionType_Args__() throws Exception {
    Short actual = target.getReligionType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSecondaryLanguageType_Args__() throws Exception {
    Short actual = target.getSecondaryLanguageType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSensitiveHlthInfoOnFileIndicator_Args__() throws Exception {
    String actual = target.getSensitiveHlthInfoOnFileIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSensitivityIndicator_Args__() throws Exception {
    String actual = target.getSensitivityIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessCode_Args__() throws Exception {
    String actual = target.getLimitedAccessCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSoc158PlacementCode_Args__() throws Exception {
    String actual = target.getSoc158PlacementCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSoc158SealedClientIndicator_Args__() throws Exception {
    String actual = target.getSoc158SealedClientIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSocialSecurityNumChangedCode_Args__() throws Exception {
    String actual = target.getSocialSecurityNumChangedCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSocialSecurityNumber_Args__() throws Exception {
    String actual = target.getSocialSecurityNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTribalAncestryClientIndicatorVar_Args__() throws Exception {
    String actual = target.getTribalAncestryClientIndicatorVar();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTribalMembrshpVerifctnIndicatorVar_Args__() throws Exception {
    String actual = target.getTribalMembrshpVerifctnIndicatorVar();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getUnemployedParentCode_Args__() throws Exception {
    String actual = target.getUnemployedParentCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZippyCreatedIndicator_Args__() throws Exception {
    String actual = target.getZippyCreatedIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguages_Args__() throws Exception {
    ApiLanguageAware[] actual = target.getLanguages();
    ApiLanguageAware[] expected = new ApiLanguageAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguages_Args2_() throws Exception {
    target.primaryLanguageType = (short) 1249;
    target.secondaryLanguageType = (short) 1260;
    ApiLanguageAware[] actual = target.getLanguages();
    assertThat(actual, notNullValue());
    assertEquals(actual[0].getLanguageSysId(), new Integer(target.primaryLanguageType));
    assertEquals(actual[1].getLanguageSysId(), new Integer(target.secondaryLanguageType));
    assertTrue(actual[0].getPrimary());
    assertFalse(actual[1].getPrimary());
  }

  @Test
  public void getSerialversionuid_Args__() throws Exception {
    long actual = BaseClient.getSerialversionuid();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLogger_Args__() throws Exception {
    Logger actual = BaseClient.getLogger();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAdjudicatedDelinquentIndicator_Args__String() throws Exception {
    String adjudicatedDelinquentIndicator = null;
    target.setAdjudicatedDelinquentIndicator(adjudicatedDelinquentIndicator);
  }

  @Test
  public void setAdoptionStatusCode_Args__String() throws Exception {
    String adoptionStatusCode = null;
    target.setAdoptionStatusCode(adoptionStatusCode);
  }

  @Test
  public void setAlienRegistrationNumber_Args__String() throws Exception {
    String alienRegistrationNumber = null;
    target.setAlienRegistrationNumber(alienRegistrationNumber);
  }

  @Test
  public void setBirthCity_Args__String() throws Exception {
    String birthCity = null;
    target.setBirthCity(birthCity);
  }

  @Test
  public void setBirthCountryCodeType_Args__Short() throws Exception {
    Short birthCountryCodeType = null;
    target.setBirthCountryCodeType(birthCountryCodeType);
  }

  @Test
  public void setBirthDate_Args__Date() throws Exception {
    Date birthDate = mock(Date.class);
    target.setBirthDate(birthDate);
  }

  @Test
  public void setBirthFacilityName_Args__String() throws Exception {
    String birthFacilityName = null;
    target.setBirthFacilityName(birthFacilityName);
  }

  @Test
  public void setBirthStateCodeType_Args__Short() throws Exception {
    Short birthStateCodeType = null;
    target.setBirthStateCodeType(birthStateCodeType);
  }

  @Test
  public void setBirthplaceVerifiedIndicator_Args__String() throws Exception {
    String birthplaceVerifiedIndicator = null;
    target.setBirthplaceVerifiedIndicator(birthplaceVerifiedIndicator);
  }

  @Test
  public void setChildClientIndicatorVar_Args__String() throws Exception {
    String childClientIndicatorVar = null;
    target.setChildClientIndicatorVar(childClientIndicatorVar);
  }

  @Test
  public void setClientIndexNumber_Args__String() throws Exception {
    String clientIndexNumber = null;
    target.setClientIndexNumber(clientIndexNumber);
  }

  @Test
  public void setCommentDescription_Args__String() throws Exception {
    String commentDescription = null;
    target.setCommentDescription(commentDescription);
  }

  @Test
  public void setCommonFirstName_Args__String() throws Exception {
    String commonFirstName = null;
    target.setCommonFirstName(commonFirstName);
  }

  @Test
  public void setCommonLastName_Args__String() throws Exception {
    String commonLastName = null;
    target.setCommonLastName(commonLastName);
  }

  @Test
  public void setCommonMiddleName_Args__String() throws Exception {
    String commonMiddleName = null;
    target.setCommonMiddleName(commonMiddleName);
  }

  @Test
  public void setConfidentialityActionDate_Args__Date() throws Exception {
    Date confidentialityActionDate = mock(Date.class);
    target.setConfidentialityActionDate(confidentialityActionDate);
  }

  @Test
  public void setConfidentialityInEffectIndicator_Args__String() throws Exception {
    String confidentialityInEffectIndicator = null;
    target.setConfidentialityInEffectIndicator(confidentialityInEffectIndicator);
  }

  @Test
  public void setCreationDate_Args__Date() throws Exception {
    Date creationDate = mock(Date.class);
    target.setCreationDate(creationDate);
  }

  @Test
  public void setCurrCaChildrenServIndicator_Args__String() throws Exception {
    String currCaChildrenServIndicator = null;
    target.setCurrCaChildrenServIndicator(currCaChildrenServIndicator);
  }

  @Test
  public void setCurrentlyOtherDescription_Args__String() throws Exception {
    String currentlyOtherDescription = null;
    target.setCurrentlyOtherDescription(currentlyOtherDescription);
  }

  @Test
  public void setCurrentlyRegionalCenterIndicator_Args__String() throws Exception {
    String currentlyRegionalCenterIndicator = null;
    target.setCurrentlyRegionalCenterIndicator(currentlyRegionalCenterIndicator);
  }

  @Test
  public void setDeathDate_Args__Date() throws Exception {
    Date deathDate = mock(Date.class);
    target.setDeathDate(deathDate);
  }

  @Test
  public void setDeathDateVerifiedIndicator_Args__String() throws Exception {
    String deathDateVerifiedIndicator = null;
    target.setDeathDateVerifiedIndicator(deathDateVerifiedIndicator);
  }

  @Test
  public void setDeathPlace_Args__String() throws Exception {
    String deathPlace = null;
    target.setDeathPlace(deathPlace);
  }

  @Test
  public void setDeathReasonText_Args__String() throws Exception {
    String deathReasonText = null;
    target.setDeathReasonText(deathReasonText);
  }

  @Test
  public void setDriverLicenseNumber_Args__String() throws Exception {
    String driverLicenseNumber = null;
    target.setDriverLicenseNumber(driverLicenseNumber);
  }

  @Test
  public void setDriverLicenseStateCodeType_Args__Short() throws Exception {
    Short driverLicenseStateCodeType = null;
    target.setDriverLicenseStateCodeType(driverLicenseStateCodeType);
  }

  @Test
  public void setEmailAddress_Args__String() throws Exception {
    String emailAddress = null;
    target.setEmailAddress(emailAddress);
  }

  @Test
  public void setEstimatedDobCode_Args__String() throws Exception {
    String estimatedDobCode = null;
    target.setEstimatedDobCode(estimatedDobCode);
  }

  @Test
  public void setEthUnableToDetReasonCode_Args__String() throws Exception {
    String ethUnableToDetReasonCode = null;
    target.setEthUnableToDetReasonCode(ethUnableToDetReasonCode);
  }

  @Test
  public void setFatherParentalRightTermDate_Args__Date() throws Exception {
    Date fatherParentalRightTermDate = mock(Date.class);
    target.setFatherParentalRightTermDate(fatherParentalRightTermDate);
  }

  @Test
  public void setGenderCode_Args__String() throws Exception {
    String genderCode = null;
    target.setGenderCode(genderCode);
  }

  @Test
  public void setHealthSummaryText_Args__String() throws Exception {
    String healthSummaryText = null;
    target.setHealthSummaryText(healthSummaryText);
  }

  @Test
  public void setHispUnableToDetReasonCode_Args__String() throws Exception {
    String hispUnableToDetReasonCode = null;
    target.setHispUnableToDetReasonCode(hispUnableToDetReasonCode);
  }

  @Test
  public void setHispanicOriginCode_Args__String() throws Exception {
    String hispanicOriginCode = null;
    target.setHispanicOriginCode(hispanicOriginCode);
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setImmigrationCountryCodeType_Args__Short() throws Exception {
    Short immigrationCountryCodeType = null;
    target.setImmigrationCountryCodeType(immigrationCountryCodeType);
  }

  @Test
  public void setImmigrationStatusType_Args__Short() throws Exception {
    Short immigrationStatusType = null;
    target.setImmigrationStatusType(immigrationStatusType);
  }

  @Test
  public void setIncapacitatedParentCode_Args__String() throws Exception {
    String incapacitatedParentCode = null;
    target.setIncapacitatedParentCode(incapacitatedParentCode);
  }

  @Test
  public void setIndividualHealthCarePlanIndicator_Args__String() throws Exception {
    String individualHealthCarePlanIndicator = null;
    target.setIndividualHealthCarePlanIndicator(individualHealthCarePlanIndicator);
  }

  @Test
  public void setLimitationOnScpHealthIndicator_Args__String() throws Exception {
    String limitationOnScpHealthIndicator = null;
    target.setLimitationOnScpHealthIndicator(limitationOnScpHealthIndicator);
  }

  @Test
  public void setLiterateCode_Args__String() throws Exception {
    String literateCode = null;
    target.setLiterateCode(literateCode);
  }

  @Test
  public void setMaritalCohabitatnHstryIndicatorVar_Args__String() throws Exception {
    String maritalCohabitatnHstryIndicatorVar = null;
    target.setMaritalCohabitatnHstryIndicatorVar(maritalCohabitatnHstryIndicatorVar);
  }

  @Test
  public void setMaritalStatusType_Args__Short() throws Exception {
    Short maritalStatusType = null;
    target.setMaritalStatusType(maritalStatusType);
  }

  @Test
  public void setMilitaryStatusCode_Args__String() throws Exception {
    String militaryStatusCode = null;
    target.setMilitaryStatusCode(militaryStatusCode);
  }

  @Test
  public void setMotherParentalRightTermDate_Args__Date() throws Exception {
    Date motherParentalRightTermDate = mock(Date.class);
    target.setMotherParentalRightTermDate(motherParentalRightTermDate);
  }

  @Test
  public void setNamePrefixDescription_Args__String() throws Exception {
    String namePrefixDescription = null;
    target.setNamePrefixDescription(namePrefixDescription);
  }

  @Test
  public void setNameType_Args__Short() throws Exception {
    Short nameType = null;
    target.setNameType(nameType);
  }

  @Test
  public void setOutstandingWarrantIndicator_Args__String() throws Exception {
    String outstandingWarrantIndicator = null;
    target.setOutstandingWarrantIndicator(outstandingWarrantIndicator);
  }

  @Test
  public void setPrevCaChildrenServIndicator_Args__String() throws Exception {
    String prevCaChildrenServIndicator = null;
    target.setPrevCaChildrenServIndicator(prevCaChildrenServIndicator);
  }

  @Test
  public void setPrevOtherDescription_Args__String() throws Exception {
    String prevOtherDescription = null;
    target.setPrevOtherDescription(prevOtherDescription);
  }

  @Test
  public void setPrevRegionalCenterIndicator_Args__String() throws Exception {
    String prevRegionalCenterIndicator = null;
    target.setPrevRegionalCenterIndicator(prevRegionalCenterIndicator);
  }

  @Test
  public void setPrimaryEthnicityType_Args__Short() throws Exception {
    Short primaryEthnicityType = null;
    target.setPrimaryEthnicityType(primaryEthnicityType);
  }

  @Test
  public void setPrimaryLanguageType_Args__Short() throws Exception {
    Short primaryLanguageType = null;
    target.setPrimaryLanguageType(primaryLanguageType);
  }

  @Test
  public void setReligionType_Args__Short() throws Exception {
    Short religionType = null;
    target.setReligionType(religionType);
  }

  @Test
  public void setSecondaryLanguageType_Args__Short() throws Exception {
    Short secondaryLanguageType = null;
    target.setSecondaryLanguageType(secondaryLanguageType);
  }

  @Test
  public void setSensitiveHlthInfoOnFileIndicator_Args__String() throws Exception {
    String sensitiveHlthInfoOnFileIndicator = null;
    target.setSensitiveHlthInfoOnFileIndicator(sensitiveHlthInfoOnFileIndicator);
  }

  @Test
  public void setSensitivityIndicator_Args__String() throws Exception {
    String sensitivityIndicator = null;
    target.setSensitivityIndicator(sensitivityIndicator);
  }

  @Test
  public void setSoc158PlacementCode_Args__String() throws Exception {
    String soc158PlacementCode = null;
    target.setSoc158PlacementCode(soc158PlacementCode);
  }

  @Test
  public void setSoc158SealedClientIndicator_Args__String() throws Exception {
    String soc158SealedClientIndicator = null;
    target.setSoc158SealedClientIndicator(soc158SealedClientIndicator);
  }

  @Test
  public void setSocialSecurityNumChangedCode_Args__String() throws Exception {
    String socialSecurityNumChangedCode = null;
    target.setSocialSecurityNumChangedCode(socialSecurityNumChangedCode);
  }

  @Test
  public void setSocialSecurityNumber_Args__String() throws Exception {
    String socialSecurityNumber = null;
    target.setSocialSecurityNumber(socialSecurityNumber);
  }

  @Test
  public void setSuffixTitleDescription_Args__String() throws Exception {
    String suffixTitleDescription = null;
    target.setSuffixTitleDescription(suffixTitleDescription);
  }

  @Test
  public void setTribalAncestryClientIndicatorVar_Args__String() throws Exception {
    String tribalAncestryClientIndicatorVar = null;
    target.setTribalAncestryClientIndicatorVar(tribalAncestryClientIndicatorVar);
  }

  @Test
  public void setTribalMembrshpVerifctnIndicatorVar_Args__String() throws Exception {
    String tribalMembrshpVerifctnIndicatorVar = null;
    target.setTribalMembrshpVerifctnIndicatorVar(tribalMembrshpVerifctnIndicatorVar);
  }

  @Test
  public void setUnemployedParentCode_Args__String() throws Exception {
    String unemployedParentCode = null;
    target.setUnemployedParentCode(unemployedParentCode);
  }

  @Test
  public void setZippyCreatedIndicator_Args__String() throws Exception {
    String zippyCreatedIndicator = null;
    target.setZippyCreatedIndicator(zippyCreatedIndicator);
  }

  private static final class TestOnlyClient extends BaseClient {
    // whatever

    /**
     * Default constructor
     */
    public TestOnlyClient() {
      super();
    }

    /**
     * constructor
     *
     * @param lastUpdatedId the last updated Id
     */
    public TestOnlyClient(String lastUpdatedId) {
      super(lastUpdatedId);
    }

    /**
     * constructor
     *
     * @param lastUpdatedId the last updated Id
     * @param lastUpdatedTime the last updated time
     */
    public TestOnlyClient(String lastUpdatedId, Date lastUpdatedTime) {
      super(lastUpdatedId, lastUpdatedTime);
    }
  }

}
