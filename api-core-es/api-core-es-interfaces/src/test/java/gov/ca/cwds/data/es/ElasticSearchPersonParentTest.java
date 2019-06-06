package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElasticSearchPersonParentTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonParent.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLegacyClientId_Args__() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    String actual = target.getLegacyClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyLastUpdated_Args__() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    String actual = target.getLegacyLastUpdated();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacySourceTable_Args__() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getRelationship_Args__() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    String actual = target.getRelationship();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationship_Args__String() throws Exception {
    ElasticSearchPersonParent target = new ElasticSearchPersonParent();
    String relationship = null;
    target.setRelationship(relationship);
  }

}
