package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.std.ApiPhoneAware.PhoneType;

public class ElasticSearchPersonPhoneTest {

  ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();

  @Before
  public void setup() {
    target = new ElasticSearchPersonPhone();
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonPhone.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void getPhoneNumber_Args__() throws Exception {
    String actual = target.getPhoneNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberExtension_Args__() throws Exception {
    String actual = target.getPhoneNumberExtension();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_Args__() throws Exception {
    PhoneType actual = target.getPhoneType();
    PhoneType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_2() throws Exception {
    target.setPhoneType(PhoneType.Cell);
    PhoneType actual = target.getPhoneType();
    PhoneType expected = PhoneType.Cell;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumber_Args__String() throws Exception {
    String phoneNumber = null;
    target.setPhoneNumber(phoneNumber);
  }

  @Test
  public void getPhoneNumberExtension_Args__String() throws Exception {
    String phoneNumberExtension = null;
    target.getPhoneNumberExtension(phoneNumberExtension);
  }

  @Test
  public void setPhoneType_Args__PhoneType() throws Exception {
    target.setPhoneType(PhoneType.Cell);
  }

  @Test
  public void getPhoneId_Args__() throws Exception {
    String actual = target.getPhoneId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumberExtension_Args__String() throws Exception {
    String phoneNumberExtension = null;
    target.setPhoneNumberExtension(phoneNumberExtension);
  }

}
