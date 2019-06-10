package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElasticSearchAccessLimitationTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchAccessLimitation.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLimitedAccessCode_Args__() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String actual = target.getLimitedAccessCode();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessCode_Args__String() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String limitedAccessCode = null;
    target.setLimitedAccessCode(limitedAccessCode);

    String actual = target.getLimitedAccessCode();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLimitedAccessDate_Args__() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String actual = target.getLimitedAccessDate();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessDate_Args__String() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String limitedAccessDate = null;
    target.setLimitedAccessDate(limitedAccessDate);
  }

  @Test
  public void getLimitedAccessDescription_Args__() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String actual = target.getLimitedAccessDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessDescription_Args__String() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String limitedAccessDescription = null;
    target.setLimitedAccessDescription(limitedAccessDescription);
  }

  @Test
  public void getLimitedAccessGovernmentEntityId_Args__() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String actual = target.getLimitedAccessGovernmentEntityId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessGovernmentEntityId_Args__String() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String limitedAccessGovernmentEntityId = null;
    target.setLimitedAccessGovernmentEntityId(limitedAccessGovernmentEntityId);
  }

  @Test
  public void getLimitedAccessGovernmentEntityName_Args__() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String actual = target.getLimitedAccessGovernmentEntityName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLimitedAccessGovernmentEntityName_Args__String() throws Exception {
    ElasticSearchAccessLimitation target = new ElasticSearchAccessLimitation();
    String limitedAccessGovernmentEntityName = null;
    target.setLimitedAccessGovernmentEntityName(limitedAccessGovernmentEntityName);
  }

}
