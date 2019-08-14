package gov.ca.cwds.tracelog;

public interface TraceLogRequestContext {

  /**
   * Get user id, if stored.
   * 
   * @return The user id
   */
  String getUserId();

  /**
   * Get registered instance of TraceLogRequestContext.
   * 
   * @return TraceLogRequestContext instance
   */
  static TraceLogRequestContext instance() {
    return SimpleTraceLogRequestContext.instance();
  }

}
