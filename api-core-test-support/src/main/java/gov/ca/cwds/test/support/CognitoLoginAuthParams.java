package gov.ca.cwds.test.support;

/**
 * 
 * @author CWDS API Team
 *
 */
public class CognitoLoginAuthParams implements AuthParams {
  private String user;
  private String password;
  private String code;
  private String url;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
