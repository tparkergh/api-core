package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ElasticSearchPersonCsecTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonCsec.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String actual = target.getStartDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__String() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String startDate = null;
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String actual = target.getEndDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__String() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String endDate = null;
    target.setEndDate(endDate);
  }

  @Test
  public void getCsecCodeId_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String actual = target.getCsecCodeId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCsecCodeId_Args__String() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String csecCodeId = null;
    target.setCsecCodeId(csecCodeId);
  }

  @Test
  public void getCsecDesc_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String actual = target.getCsecDesc();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCsecDesc_Args__String() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    String csecDesc = null;
    target.setCsecDesc(csecDesc);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonCsec target = new ElasticSearchPersonCsec();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
