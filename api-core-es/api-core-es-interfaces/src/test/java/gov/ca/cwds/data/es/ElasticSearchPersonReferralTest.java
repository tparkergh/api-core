package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;

public class ElasticSearchPersonReferralTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonReferral.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyLastUpdated_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getLegacyLastUpdated();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyLastUpdated_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String legacyLastUpdated = null;
    target.setLegacyLastUpdated(legacyLastUpdated);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getStartDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String startDate = null;
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getEndDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String endDate = null;
    target.setEndDate(endDate);
  }

  @Test
  public void getCountyName_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getCountyName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyName_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String countyName = null;
    target.setCountyName(countyName);
  }

  @Test
  public void getResponseTime_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getResponseTime();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setResponseTime_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String responseTime = null;
    target.setResponseTime(responseTime);
  }

  @Test
  public void getReporter_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchPersonReporter actual = target.getReporter();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setReporter_Args__ElasticSearchPersonReporter() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchPersonReporter reporter = mock(ElasticSearchPersonReporter.class);
    target.setReporter(reporter);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchPersonSocialWorker actual = target.getAssignedSocialWorker();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAssignedSocialWorker_Args__ElasticSearchPersonSocialWorker() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchPersonSocialWorker assignedSocialWorker =
        mock(ElasticSearchPersonSocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

  @Test
  public void getAllegations_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    List<ElasticSearchPersonAllegation> actual = target.getAllegations();
    List<ElasticSearchPersonAllegation> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegations_Args__List() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    List<ElasticSearchPersonAllegation> allegations =
        new ArrayList<ElasticSearchPersonAllegation>();
    target.setAllegations(allegations);
  }

  @Test
  public void getAccessLimitation_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchAccessLimitation actual = target.getAccessLimitation();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAccessLimitation_Args__ElasticSearchAccessLimitation() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchAccessLimitation accessLimitation = mock(ElasticSearchAccessLimitation.class);
    target.setAccessLimitation(accessLimitation);
  }

  @Test
  public void getCountyId_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getCountyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyId_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String countyId = null;
    target.setCountyId(countyId);
  }

  @Test
  public void getResponseTimeId_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String actual = target.getResponseTimeId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setResponseTimeId_Args__String() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    String responseTimeId = null;
    target.setResponseTimeId(responseTimeId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonReferral target = new ElasticSearchPersonReferral();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
