package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElasticSearchLegacyDescriptorTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchLegacyDescriptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyUiId_Args__() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String actual = target.getLegacyUiId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyUiId_Args__String() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String legacyUiId = null;
    target.setLegacyUiId(legacyUiId);
  }

  @Test
  public void getLegacyLastUpdated_Args__() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String actual = target.getLegacyLastUpdated();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyLastUpdated_Args__String() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String legacyLastUpdated = null;
    target.setLegacyLastUpdated(legacyLastUpdated);
  }

  @Test
  public void getLegacyTableName_Args__() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String actual = target.getLegacyTableName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyTableName_Args__String() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String legacyTableName = null;
    target.setLegacyTableName(legacyTableName);
  }

  @Test
  public void getLegacyTableDescription_Args__() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String actual = target.getLegacyTableDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyTableDescription_Args__String() throws Exception {
    ElasticSearchLegacyDescriptor target = new ElasticSearchLegacyDescriptor();
    String legacyTableDescription = null;
    target.setLegacyTableDescription(legacyTableDescription);
  }

}
