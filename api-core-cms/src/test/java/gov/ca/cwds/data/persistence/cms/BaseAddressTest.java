package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import org.junit.Test;

import gov.ca.cwds.data.std.ApiPhoneAware;

public class BaseAddressTest {

  private static final class TestOnlyBaseAddress extends BaseAddress {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseAddress.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getCity_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getCity();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCity_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String city = null;
    target.setCity(city);
  }

  @Test
  public void getEmergencyNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Long actual = target.getEmergencyNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmergencyExtension_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer actual = target.getEmergencyExtension();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEmergencyExtension_Args__Integer() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer emergencyExtension = null;
    target.setEmergencyExtension(emergencyExtension);
  }

  @Test
  public void getMessageNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Long actual = target.getMessageNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMessageNumber_Args__BigDecimal() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Long messageNumber = null;
    target.setMessageNumber(messageNumber);
  }

  @Test
  public void getMessageExtension_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer actual = target.getMessageExtension();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMessageExtension_Args__Integer() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer messageExtension = null;
    target.setMessageExtension(messageExtension);
  }

  @Test
  public void getHeaderAddress_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getHeaderAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHeaderAddress_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String headerAddress = null;
    target.setHeaderAddress(headerAddress);
  }

  @Test
  public void getPrimaryNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Long actual = target.getPrimaryNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryNumber_Args__BigDecimal() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Long primaryNumber = null;
    target.setPrimaryNumber(primaryNumber);
  }

  @Test
  public void getPrimaryExtension_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer actual = target.getPrimaryExtension();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrimaryExtension_Args__Integer() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Integer primaryExtension = null;
    target.setPrimaryExtension(primaryExtension);
  }

  @Test
  public void getState_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_Args2_() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    target.state = 1860;
    String actual = target.getState();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getStateCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStateCd_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short state = null;
    target.setStateCd(state);
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getStreetName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetName_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getStreetNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetNumber_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void getZip_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getZip();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setZip_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String zip = null;
    target.setZip(zip);
  }

  @Test
  public void getAddressDescription_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getAddressDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAddressDescription_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String addressDescription = null;
    target.setAddressDescription(addressDescription);
  }

  @Test
  public void getZip4_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getZip4();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setZip4_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short zip4 = null;
    target.setZip4(zip4);
  }

  @Test
  public void getPostDirCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getPostDirCd();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPostDirCd_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String postDirCd = null;
    target.setPostDirCd(postDirCd);
  }

  @Test
  public void getPreDirCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getPreDirCd();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPreDirCd_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String preDirCd = null;
    target.setPreDirCd(preDirCd);
  }

  @Test
  public void getStreetSuffixCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getStreetSuffixCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetSuffixCd_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short streetSuffixCd = null;
    target.setStreetSuffixCd(streetSuffixCd);
  }

  @Test
  public void getUnitDesignationCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getUnitDesignationCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUnitDesignationCd_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short unitDesignationCd = null;
    target.setUnitDesignationCd(unitDesignationCd);
  }

  @Test
  public void getUnitNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getUnitNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUnitNumber_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String unitNumber = null;
    target.setUnitNumber(unitNumber);
  }

  @Test
  public void getGovernmentEntityCd_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getGovernmentEntityCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGovernmentEntityCd_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short governmentEntityCd = null;
    target.setGovernmentEntityCd(governmentEntityCd);
  }

  @Test
  public void getFrgAdrtB_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getFrgAdrtB();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFrgAdrtB_Args__String() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String frgAdrtB = null;
    target.setFrgAdrtB(frgAdrtB);
  }

  @Test
  public void setState_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short state = null;
    target.setState(state);
  }

  @Test
  public void getPhones_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    ApiPhoneAware[] actual = target.getPhones();
    ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args2_() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();

    target.primaryExtension = 123;
    target.primaryNumber = 4083742790L;

    target.messageExtension = 456;
    target.messageNumber = 9163742790L;

    target.emergencyExtension = 789;
    target.emergencyNumber = 5303742790L;

    ApiPhoneAware[] actual = target.getPhones();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getStreetAddress_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getStreetAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetAddress_Args2_() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    target.streetName = "main";
    target.streetNumber = "123";
    target.unitNumber = "45";

    String actual = target.getStreetAddress();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getCounty_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddressId_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrZip4_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getApiAdrZip4();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitNumber_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    String actual = target.getApiAdrUnitNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrAddressType_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getApiAdrAddressType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitType_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getApiAdrUnitType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getContextAddressType_Args__() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short actual = target.getContextAddressType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setContextAddressType_Args__Short() throws Exception {
    BaseAddress target = new TestOnlyBaseAddress();
    Short contextAddressType = null;
    target.setContextAddressType(contextAddressType);
  }

}
