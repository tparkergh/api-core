package gov.ca.cwds.auth;

@SuppressWarnings({"squid:MaximumInheritanceDepth", "serial"})
public class InvalidTokenException extends ApiAuthenticationException {

  /**
   * Creates a new InvalidTokenException.
   */
  public InvalidTokenException() {
    super();
  }

  /**
   * Constructs a new InvalidTokenException.
   *
   * @param message the reason for the exception
   * @param cause   the underlying Throwable that caused this exception to be thrown.
   */
  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new InvalidTokenException.
   *
   * @param message the reason for the exception
   */
  public InvalidTokenException(String message) {
    super(message);
  }

  /**
   * Constructs a new InvalidTokenException.
   *
   * @param cause   the underlying Throwable that caused this exception to be thrown.
   */
  public InvalidTokenException(Throwable cause) {
    super(cause);
  }

}
