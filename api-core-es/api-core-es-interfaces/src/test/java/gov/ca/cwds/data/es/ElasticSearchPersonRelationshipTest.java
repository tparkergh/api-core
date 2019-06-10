package gov.ca.cwds.data.es;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import io.dropwizard.testing.FixtureHelpers;
import org.junit.Before;
import org.junit.Test;

public class ElasticSearchPersonRelationshipTest {

  ElasticSearchPersonRelationship target;

  @Before
  public void setup() throws Exception {
    target = new ElasticSearchPersonRelationship();
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonRelationship.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getRelatedPersonId_Args__() throws Exception {
    String actual = target.getRelatedPersonId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonId_Args__String() throws Exception {
    String relatedPersonId = null;
    target.setRelatedPersonId(relatedPersonId);
  }

  @Test
  public void getRelatedPersonFirstName_Args__() throws Exception {
    String actual = target.getRelatedPersonFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonFirstName_Args__String() throws Exception {
    String relatedPersonFirstName = null;
    target.setRelatedPersonFirstName(relatedPersonFirstName);
  }

  @Test
  public void getRelatedPersonLastName_Args__() throws Exception {
    String actual = target.getRelatedPersonLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonLastName_Args__String() throws Exception {
    String relatedPersonLastName = null;
    target.setRelatedPersonLastName(relatedPersonLastName);
  }

  @Test
  public void getIndexedPersonRelationship_Args__() throws Exception {
    String actual = target.getIndexedPersonRelationship();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIndexedPersonRelationship_Args__String() throws Exception {
    String indexedPersonRelationship = null;
    target.setIndexedPersonRelationship(indexedPersonRelationship);
  }

  @Test
  public void getRelatedPersonLegacyId_Args__() throws Exception {
    String actual = target.getRelatedPersonLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonLegacyId_Args__String() throws Exception {
    String relatedPersonLegacyId = null;
    target.setRelatedPersonLegacyId(relatedPersonLegacyId);
  }

  @Test
  public void getRelatedPersonLegacySourceTable_Args__() throws Exception {
    String actual = target.getRelatedPersonLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonLegacySourceTable_Args__String() throws Exception {
    String relatedPersonLegacySourceTable = null;
    target.setRelatedPersonLegacySourceTable(relatedPersonLegacySourceTable);
  }

  @Test
  public void getRelatedPersonRelationship_Args__() throws Exception {
    String actual = target.getRelatedPersonRelationship();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelatedPersonRelationship_Args__String() throws Exception {
    String relatedPersonRelationship = null;
    target.setRelatedPersonRelationship(relatedPersonRelationship);
  }

  @Test
  public void getRelationshipContext_Args__() throws Exception {
    String actual = target.getRelationshipContext();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationshipContext_Args__String() throws Exception {
    String relationshipContext = null;
    target.setRelationshipContext(relationshipContext);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void deserialize() throws Exception {
    final ElasticSearchPerson person = ElasticSearchPerson.MAPPER.readValue(fixture(
        "fixtures/data/es/multiple_relations.json"),
        ElasticSearchPerson.class);
    assertThat(person, notNullValue());
  }

}
