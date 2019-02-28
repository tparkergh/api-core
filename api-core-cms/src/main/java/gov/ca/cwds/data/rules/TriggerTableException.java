package gov.ca.cwds.data.rules;

import gov.ca.cwds.data.DaoException;

/**
 * Base exception class for CWDS API Trigger Table exceptions.
 * 
 * <p>
 * Indicates that an API operation has encountered a possible yet unexpected condition, rather than
 * a generic Java exception.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings({"serial", "squid:MaximumInheritanceDepth"})
public class TriggerTableException extends DaoException {

  /**
   * Construct an API Trigger Table exception.
   */
  public TriggerTableException() {
    super();
  }

  /**
   * Construct with a displayable message.
   * 
   * @param message message to display
   */
  public TriggerTableException(String message) {
    super(message);
  }

  /**
   * Construct with a Throwable to wrap and rethrow.
   * 
   * @param cause Throwable to rethrow
   */
  public TriggerTableException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct with a displayable message and Throwable to wrap and rethrow.
   * 
   * @param message message to display
   * @param cause Throwable to rethrow
   */
  public TriggerTableException(String message, Throwable cause) {
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
  public TriggerTableException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
