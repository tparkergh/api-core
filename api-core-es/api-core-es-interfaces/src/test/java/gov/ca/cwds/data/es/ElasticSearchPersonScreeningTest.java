package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonSocialWorker;
import gov.ca.cwds.data.es.ElasticSearchPerson.ElasticSearchPersonStaff;

public class ElasticSearchPersonScreeningTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonScreening.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    Date actual = target.getStartDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__Date() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    Date startDate = mock(Date.class);
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void getCountyName_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getCountyName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyName_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String countyName = null;
    target.setCountyName(countyName);
  }

  @Test
  public void getDecision_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getDecision();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDecision_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String decision = null;
    target.setDecision(decision);
  }

  @Test
  public void getResponseTime_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getResponseTime();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setResponseTime_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String responseTime = null;
    target.setResponseTime(responseTime);
  }

  @Test
  public void getServiceName_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getServiceName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setServiceName_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String serviceName = null;
    target.setServiceName(serviceName);
  }

  @Test
  public void getCategory_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getCategory();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCategory_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String category = null;
    target.setCategory(category);
  }

  @Test
  public void getReporter_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonReporter actual = target.getReporter();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setReporter_Args__ElasticSearchPersonReporter() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonReporter reporter = mock(ElasticSearchPersonReporter.class);
    target.setReporter(reporter);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonSocialWorker actual = target.getAssignedSocialWorker();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAssignedSocialWorker_Args__ElasticSearchPersonSocialWorker() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonSocialWorker assignedSocialWorker =
        mock(ElasticSearchPersonSocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

  @Test
  public void getStaffName_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonStaff actual = target.getStaffName();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setStaffName_Args__ElasticSearchPersonStaff() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchPersonStaff staffName = mock(ElasticSearchPersonStaff.class);
    target.setStaffName(staffName);
  }

  @Test
  public void getAllegations_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    List<ElasticSearchPersonAllegation> actual = target.getAllegations();
    List<ElasticSearchPersonAllegation> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegations_Args__List() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    List<ElasticSearchPersonAllegation> allegations =
        new ArrayList<ElasticSearchPersonAllegation>();
    target.setAllegations(allegations);
  }

  @Test
  public void getAllPeople_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    List<ElasticSearchPersonAny> actual = target.getAllPeople();
    List<ElasticSearchPersonAny> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllPeople_Args__List() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    List<ElasticSearchPersonAny> allPeople = new ArrayList<ElasticSearchPersonAny>();
    target.setAllPeople(allPeople);
  }

  @Test
  public void getReferralId_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String actual = target.getReferralId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReferralId_Args__String() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    String referralId = null;
    target.setReferralId(referralId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonScreening target = new ElasticSearchPersonScreening();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
