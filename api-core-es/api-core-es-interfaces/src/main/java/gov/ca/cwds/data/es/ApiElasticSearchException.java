package gov.ca.cwds.data.es;

/**
 * Base exception class for CWDS API Elasticsearch exceptions.
 * 
 * <p>
 * Indicates that an API operation has encountered a possible yet unexpected exception from
 * Elasticsearch, rather than a generic Java exception, allowing callers to handle this exception
 * category as needed.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ApiElasticSearchException extends RuntimeException {

  /**
   * Construct an API service exception.
   */
  public ApiElasticSearchException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public ApiElasticSearchException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public ApiElasticSearchException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public ApiElasticSearchException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Construct and control stack traces.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   * @param enableSuppression if the JVM can suppress this exception
   * @param writableStackTrace if security permits, the JVM may log the stack trace
   */
  public ApiElasticSearchException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
