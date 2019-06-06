package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ElasticSearchPersonAkaTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonAka.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getNameType_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getNameType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setNameType_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String nameType = null;
    target.setNameType(nameType);
  }

  @Test
  public void getPrefix_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getPrefix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPrefix_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String prefix = null;
    target.setPrefix(prefix);
  }

  @Test
  public void getSuffix_Args__() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String actual = target.getSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSuffix_Args__String() throws Exception {
    ElasticSearchPersonAka target = new ElasticSearchPersonAka();
    String suffix = null;
    target.setSuffix(suffix);
  }

}
