package gov.ca.cwds.test.support;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class CognitoLoginAuthParamsTest {

  private String user;
  private String password;
  private String code;
  private String url;

  @Before
  public void setup() {
    user = "user";
    password = "password";
    code = "code";
    url = "url";
  }

  @Test
  public void settersAndGetters() {
    CognitoLoginAuthParams loginParams = new CognitoLoginAuthParams();
    loginParams.setUser(user);
    loginParams.setCode(code);
    loginParams.setPassword(password);
    loginParams.setUrl(url);
    assertEquals(loginParams.getUser(), user);
    assertEquals(loginParams.getPassword(), password);
    assertEquals(loginParams.getCode(), code);
    assertEquals(loginParams.getUrl(), url);
  }

}
