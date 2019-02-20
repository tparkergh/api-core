package gov.ca.cwds.auth.clients;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.auth.PerryShiroToken;

public class PerryClientTest {

  @Test
  public void type() throws Exception {
    assertThat(PerryClient.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    PerryClient target = new PerryClient();
    assertThat(target, notNullValue());
  }

  @Test
  public void setPerryUrl_Args__String() throws Exception {
    PerryClient target = new PerryClient();
    String perryUrl = "http://localhost:8082/perry";
    target.setPerryUrl(perryUrl);
  }

  @Test
  public void getServiceProviderId_Args__() throws Exception {
    PerryClient target = new PerryClient();
    String actual = target.getServiceProviderId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setServiceProviderId_Args__String() throws Exception {
    PerryClient target = new PerryClient();
    String serviceProviderId = null;
    target.setServiceProviderId(serviceProviderId);
  }

}
