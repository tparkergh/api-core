package gov.ca.cwds.auth;

import org.apache.shiro.authc.AuthenticationException;

@SuppressWarnings({"serial", "squid:MaximumInheritanceDepth"})
public class ApiAuthenticationException extends AuthenticationException {

  public ApiAuthenticationException() {
    super();
  }

  public ApiAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiAuthenticationException(String message) {
    super(message);
  }

  public ApiAuthenticationException(Throwable cause) {
    super(cause);
  }

}
