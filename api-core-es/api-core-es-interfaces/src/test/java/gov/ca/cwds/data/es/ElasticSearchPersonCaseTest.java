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

public class ElasticSearchPersonCaseTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonCase.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyLastUpdated_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getLegacyLastUpdated();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyLastUpdated_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String legacyLastUpdated = null;
    target.setLegacyLastUpdated(legacyLastUpdated);
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getStartDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStartDate_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String startDate = null;
    target.setStartDate(startDate);
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getEndDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEndDate_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String endDate = null;
    target.setEndDate(endDate);
  }

  @Test
  public void getCountyName_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getCountyName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyName_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String countyName = null;
    target.setCountyName(countyName);
  }

  @Test
  public void getServiceComponent_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getServiceComponent();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setServiceComponent_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String serviceComponent = null;
    target.setServiceComponent(serviceComponent);
  }

  @Test
  public void getFocusChild_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchPersonChild actual = target.getFocusChild();
    ElasticSearchPersonChild expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFocusChild_Args__ElasticSearchPersonChild() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchPersonChild focusChild = mock(ElasticSearchPersonChild.class);
    target.setFocusChild(focusChild);
  }

  @Test
  public void getAssignedSocialWorker_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchPersonSocialWorker actual = target.getAssignedSocialWorker();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAssignedSocialWorker_Args__ElasticSearchPersonSocialWorker() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchPersonSocialWorker assignedSocialWorker =
        mock(ElasticSearchPersonSocialWorker.class);
    target.setAssignedSocialWorker(assignedSocialWorker);
  }

  @Test
  public void getParents_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    List<ElasticSearchPersonParent> actual = target.getParents();
    List<ElasticSearchPersonParent> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setParents_Args__List() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    List<ElasticSearchPersonParent> parents = new ArrayList<ElasticSearchPersonParent>();
    target.setParents(parents);
  }

  @Test
  public void getAccessLimitation_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchAccessLimitation actual = target.getAccessLimitation();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAccessLimitation_Args__ElasticSearchAccessLimitation() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchAccessLimitation accessLimitation = mock(ElasticSearchAccessLimitation.class);
    target.setAccessLimitation(accessLimitation);
  }

  @Test
  public void getCountyId_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getCountyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCountyId_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String countyId = null;
    target.setCountyId(countyId);
  }

  @Test
  public void getServiceComponentId_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String actual = target.getServiceComponentId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setServiceComponentId_Args__String() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    String serviceComponentId = null;
    target.setServiceComponentId(serviceComponentId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonCase target = new ElasticSearchPersonCase();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
