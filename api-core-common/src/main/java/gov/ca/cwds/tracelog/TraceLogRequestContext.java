package gov.ca.cwds.tracelog;

/**
 * Trace log request context to find user information at runtime without additional method
 * signatures or dependency injection.
 * 
 * @author CWDS API Team
 */
public interface TraceLogRequestContext {

  /**
   * Get user id, if stored.
   * 
   * @return The user id
   */
  String getUserId();

  /**
   * Get registered instance of TraceLogRequestContext. Defaults to
   * {@link SimpleTraceLogRequestContext}.
   * 
   * @return TraceLogRequestContext instance
   */
  static TraceLogRequestContext instance() {
    return SimpleTraceLogRequestContext.instance();
  }

}
