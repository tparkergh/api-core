package gov.ca.cwds.test.support;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class JsonIdentityAuthParamsTest {

  private String identityJson;

  @Before
  public void setup() {
    identityJson = "identity";
  }

  @Test
  public void settersAndGetters() {
    JsonIdentityAuthParams jsonParams = new JsonIdentityAuthParams(identityJson);
    assertEquals(jsonParams.getIdentityJson(), identityJson);
    jsonParams.setIdentityJson("newIdentityJson");
    assertEquals("newIdentityJson", jsonParams.getIdentityJson());
  }

}
