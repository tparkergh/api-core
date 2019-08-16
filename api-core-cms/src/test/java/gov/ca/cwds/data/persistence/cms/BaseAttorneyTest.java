package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.common.CaresTestHarness;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.std.ApiPhoneAware;

public class BaseAttorneyTest extends CaresTestHarness<Client> {

  private static final class TestOnlyBaseAttorney extends BaseAttorney {
  }

  BaseAttorney target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new TestOnlyBaseAttorney();
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getArchiveAssociationIndicator_Args__() throws Exception {
    String actual = target.getArchiveAssociationIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBusinessName_Args__() throws Exception {
    String actual = target.getBusinessName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCwsAttorneyIndicator_Args__() throws Exception {
    String actual = target.getCwsAttorneyIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_Args__() throws Exception {
    Long actual = target.getFaxNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGovernmentEntityType_Args__() throws Exception {
    Short actual = target.getGovernmentEntityType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguageType_Args__() throws Exception {
    Short actual = target.getLanguageType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneExtensionNumber_Args__() throws Exception {
    Integer actual = target.getMessagePhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneNumber_Args__() throws Exception {
    assertNull(target.getMessagePhoneNumber());
  }

  @Test
  public void getMiddleInitialName_Args__() throws Exception {
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPositionTitleDescription_Args__() throws Exception {
    String actual = target.getPositionTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneExtensionNumber_Args__() throws Exception {
    assertNull(target.getPrimaryPhoneExtensionNumber());
  }

  @Test
  public void getPrimaryPhoneNumber_Args__() throws Exception {
    assertNull(target.getPrimaryPhoneNumber());
  }

  @Test
  public void getStateCodeType_Args__() throws Exception {
    assertNull(target.getStateCodeType());
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    String actual = target.getStreetNumber();
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
  public void getZipNumber_Args__() throws Exception {
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    Date actual = target.getBirthDate();
    Date expected = null;
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
  public void getPhones_Args__() throws Exception {
    ApiPhoneAware[] actual = target.getPhones();
    ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args2__() throws Exception {
    target.primaryPhoneNumber = 4083742790L;
    target.messagePhoneNumber = 4083742790L;
    ApiPhoneAware[] actual = target.getPhones();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setArchiveAssociationIndicator_Args__String() throws Exception {
    String archiveAssociationIndicator = null;
    target.setArchiveAssociationIndicator(archiveAssociationIndicator);
  }

  @Test
  public void setBusinessName_Args__String() throws Exception {
    String businessName = null;
    target.setBusinessName(businessName);
  }

  @Test
  public void setCityName_Args__String() throws Exception {
    String cityName = null;
    target.setCityName(cityName);
  }

  @Test
  public void setCwsAttorneyIndicator_Args__String() throws Exception {
    String cwsAttorneyIndicator = null;
    target.setCwsAttorneyIndicator(cwsAttorneyIndicator);
  }

  @Test
  public void setEmailAddress_Args__String() throws Exception {
    String emailAddress = null;
    target.setEmailAddress(emailAddress);
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void setGovernmentEntityType_Args__Short() throws Exception {
    Short governmentEntityType = null;
    target.setGovernmentEntityType(governmentEntityType);
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setLanguageType_Args__Short() throws Exception {
    Short languageType = null;
    target.setLanguageType(languageType);
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void setMessagePhoneExtensionNumber_Args__Integer() throws Exception {
    Integer messagePhoneExtensionNumber = null;
    target.setMessagePhoneExtensionNumber(messagePhoneExtensionNumber);
  }

  @Test
  public void setMessagePhoneNumber_Args__BigDecimal() throws Exception {
    assertNull(target.getMessagePhoneNumber());
  }

  @Test
  public void setMiddleInitialName_Args__String() throws Exception {
    String middleInitialName = null;
    target.setMiddleInitialName(middleInitialName);
  }

  @Test
  public void setNamePrefixDescription_Args__String() throws Exception {
    String namePrefixDescription = null;
    target.setNamePrefixDescription(namePrefixDescription);
  }

  @Test
  public void setPositionTitleDescription_Args__String() throws Exception {
    String positionTitleDescription = null;
    target.setPositionTitleDescription(positionTitleDescription);
  }

  @Test
  public void setPrimaryPhoneExtensionNumber_Args__Integer() throws Exception {
    Integer primaryPhoneExtensionNumber = null;
    target.setPrimaryPhoneExtensionNumber(primaryPhoneExtensionNumber);
  }

  @Test
  public void setPrimaryPhoneNumber_Args__BigDecimal() throws Exception {
    assertNull(target.getPrimaryPhoneNumber());
  }

  @Test
  public void setStateCodeType_Args__Short() throws Exception {
    Short stateCodeType = null;
    target.setStateCodeType(stateCodeType);
  }

  @Test
  public void setStreetName_Args__String() throws Exception {
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void setStreetNumber_Args__String() throws Exception {
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void setSuffixTitleDescription_Args__String() throws Exception {
    String suffixTitleDescription = null;
    target.setSuffixTitleDescription(suffixTitleDescription);
  }

  @Test
  public void setZipNumber_Args__Integer() throws Exception {
    Integer zipNumber = null;
    target.setZipNumber(zipNumber);
  }

  @Test
  public void setZipSuffixNumber_Args__Short() throws Exception {
    Short zipSuffixNumber = null;
    target.setZipSuffixNumber(zipSuffixNumber);
  }

  @Test
  public void type() throws Exception {
    assertThat(BaseAttorney.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_A$() throws Exception {
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getArchiveAssociationIndicator_A$() throws Exception {
    String actual = target.getArchiveAssociationIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBusinessName_A$() throws Exception {
    String actual = target.getBusinessName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_A$() throws Exception {
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCwsAttorneyIndicator_A$() throws Exception {
    String actual = target.getCwsAttorneyIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_A$() throws Exception {
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndDate_A$() throws Exception {
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_A$() throws Exception {
    Long actual = target.getFaxNumber();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGovernmentEntityType_A$() throws Exception {
    Short actual = target.getGovernmentEntityType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = "";;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguageType_A$() throws Exception {
    Short actual = target.getLanguageType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneExtensionNumber_A$() throws Exception {
    Integer actual = target.getMessagePhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneNumber_A$() throws Exception {
    Long actual = target.getMessagePhoneNumber();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitialName_A$() throws Exception {
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_A$() throws Exception {
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPositionTitleDescription_A$() throws Exception {
    String actual = target.getPositionTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneExtensionNumber_A$() throws Exception {
    Integer actual = target.getPrimaryPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneNumber_A$() throws Exception {
    Long actual = target.getPrimaryPhoneNumber();
    Long expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_A$() throws Exception {
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_A$() throws Exception {
    String actual = target.getStreetName();
    String expected = "";;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_A$() throws Exception {
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_A$() throws Exception {
    String actual = target.getSuffixTitleDescription();
    String expected = "";;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_A$() throws Exception {
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_A$() throws Exception {
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_A$() throws Exception {
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_A$() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_A$() throws Exception {
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_A$() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_A$() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_A$() throws Exception {
    ApiPhoneAware[] actual = target.getPhones();
    ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setArchiveAssociationIndicator_A$String() throws Exception {
    String archiveAssociationIndicator = null;
    target.setArchiveAssociationIndicator(archiveAssociationIndicator);
  }

  @Test
  public void setBusinessName_A$String() throws Exception {
    String businessName = null;
    target.setBusinessName(businessName);
  }

  @Test
  public void setCityName_A$String() throws Exception {
    String cityName = null;
    target.setCityName(cityName);
  }

  @Test
  public void setCwsAttorneyIndicator_A$String() throws Exception {
    String cwsAttorneyIndicator = null;
    target.setCwsAttorneyIndicator(cwsAttorneyIndicator);
  }

  @Test
  public void setEmailAddress_A$String() throws Exception {
    String emailAddress = null;
    target.setEmailAddress(emailAddress);
  }

  @Test
  public void setEndDate_A$Date() throws Exception {
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void setFaxNumber_A$Long() throws Exception {
    Long faxNumber = null;
    target.setFaxNumber(faxNumber);
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void setGovernmentEntityType_A$Short() throws Exception {
    Short governmentEntityType = null;
    target.setGovernmentEntityType(governmentEntityType);
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setLanguageType_A$Short() throws Exception {
    Short languageType = null;
    target.setLanguageType(languageType);
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void setMessagePhoneExtensionNumber_A$Integer() throws Exception {
    Integer messagePhoneExtensionNumber = null;
    target.setMessagePhoneExtensionNumber(messagePhoneExtensionNumber);
  }

  @Test
  public void setMessagePhoneNumber_A$Long() throws Exception {
    Long messagePhoneNumber = null;
    target.setMessagePhoneNumber(messagePhoneNumber);
  }

  @Test
  public void setMiddleInitialName_A$String() throws Exception {
    String middleInitialName = null;
    target.setMiddleInitialName(middleInitialName);
  }

  @Test
  public void setNamePrefixDescription_A$String() throws Exception {
    String namePrefixDescription = null;
    target.setNamePrefixDescription(namePrefixDescription);
  }

  @Test
  public void setPositionTitleDescription_A$String() throws Exception {
    String positionTitleDescription = null;
    target.setPositionTitleDescription(positionTitleDescription);
  }

  @Test
  public void setPrimaryPhoneExtensionNumber_A$Integer() throws Exception {
    Integer primaryPhoneExtensionNumber = null;
    target.setPrimaryPhoneExtensionNumber(primaryPhoneExtensionNumber);
  }

  @Test
  public void setPrimaryPhoneNumber_A$Long() throws Exception {
    Long primaryPhoneNumber = null;
    target.setPrimaryPhoneNumber(primaryPhoneNumber);
  }

  @Test
  public void setStateCodeType_A$Short() throws Exception {
    Short stateCodeType = null;
    target.setStateCodeType(stateCodeType);
  }

  @Test
  public void setStreetName_A$String() throws Exception {
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void setStreetNumber_A$String() throws Exception {
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void setSuffixTitleDescription_A$String() throws Exception {
    String suffixTitleDescription = null;
    target.setSuffixTitleDescription(suffixTitleDescription);
  }

  @Test
  public void setZipNumber_A$Integer() throws Exception {
    Integer zipNumber = null;
    target.setZipNumber(zipNumber);
  }

  @Test
  public void setZipSuffixNumber_A$Short() throws Exception {
    Short zipSuffixNumber = null;
    target.setZipSuffixNumber(zipSuffixNumber);
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    int expected = 0;
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

}
