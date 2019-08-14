package gov.ca.cwds.tracelog;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public class SimpleTraceLogRequestContext implements TraceLogRequestContext {

  private final String userId;

  public SimpleTraceLogRequestContext(String userId) {
    this.userId = userId;
  }

  /**
   * Get registered instance of TraceLogRequestContext.
   * 
   * @return TraceLogRequestContext instance
   */
  static TraceLogRequestContext instance() {
    final String userId = MDC.get("userId");
    return new SimpleTraceLogRequestContext(StringUtils.isNotBlank(userId) ? userId : "system");
  }

  @Override
  public String getUserId() {
    return userId;
  }

}
