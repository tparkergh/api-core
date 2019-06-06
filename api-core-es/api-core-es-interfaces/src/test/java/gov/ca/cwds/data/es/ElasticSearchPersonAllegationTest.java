package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ElasticSearchPersonAllegationTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonAllegation.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    assertThat(target, notNullValue());
  }

  @Test
  public void getVictim_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchPersonNestedPerson actual = target.getVictim();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setVictim_Args__ElasticSearchPersonNestedPerson() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchPersonNestedPerson victim = mock(ElasticSearchPersonNestedPerson.class);
    target.setVictim(victim);
  }

  @Test
  public void getPerpetrator_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchPersonNestedPerson actual = target.getPerpetrator();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setPerpetrator_Args__ElasticSearchPersonNestedPerson() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchPersonNestedPerson perpetrator = mock(ElasticSearchPersonNestedPerson.class);
    target.setPerpetrator(perpetrator);
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getAllegationDescription_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getAllegationDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAllegationDescription_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String allegationDescription = null;
    target.setAllegationDescription(allegationDescription);
  }

  @Test
  public void getDispositionDescription_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getDispositionDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDispositionDescription_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String dispositionDescription = null;
    target.setDispositionDescription(dispositionDescription);
  }

  @Test
  public void getDispositionId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getDispositionId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDispositionId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String dispositionId = null;
    target.setDispositionId(dispositionId);
  }

  @Test
  public void getPerpetratorId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getPerpetratorId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetratorId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String perpetratorId = null;
    target.setPerpetratorId(perpetratorId);
  }

  @Test
  public void getPerpetratorFirstName_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getPerpetratorFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetratorFirstName_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String perpetratorFirstName = null;
    target.setPerpetratorFirstName(perpetratorFirstName);
  }

  @Test
  public void getPerpetratorLastName_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getPerpetratorLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetratorLastName_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String perpetratorLastName = null;
    target.setPerpetratorLastName(perpetratorLastName);
  }

  @Test
  public void getPerpetratorLegacyClientId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getPerpetratorLegacyClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPerpetratorLegacyClientId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String perpetratorLegacyClientId = null;
    target.setPerpetratorLegacyClientId(perpetratorLegacyClientId);
  }

  @Test
  public void getVictimId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getVictimId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictimId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String victimId = null;
    target.setVictimId(victimId);
  }

  @Test
  public void getVictimFirstName_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getVictimFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictimFirstName_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String victimFirstName = null;
    target.setVictimFirstName(victimFirstName);
  }

  @Test
  public void getVictimLastName_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getVictimLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictimLastName_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String victimLastName = null;
    target.setVictimLastName(victimLastName);
  }

  @Test
  public void getVictimLegacyClientId_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String actual = target.getVictimLegacyClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setVictimLegacyClientId_Args__String() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    String victimLegacyClientId = null;
    target.setVictimLegacyClientId(victimLegacyClientId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonAllegation target = new ElasticSearchPersonAllegation();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
