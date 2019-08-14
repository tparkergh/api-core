package gov.ca.cwds.tracelog;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * Simplistic request context reads the current user id from the SLF4J MDC.
 * 
 * @author CWDS API Team
 */
public class SimpleTraceLogRequestContext implements TraceLogRequestContext {

  private static SimpleTraceLogRequestContext unicorn = new SimpleTraceLogRequestContext();

  private SimpleTraceLogRequestContext() {}

  /**
   * Get "registered" instance of TraceLogRequestContext.
   * 
   * @return TraceLogRequestContext instance
   */
  public static TraceLogRequestContext instance() {
    return unicorn;
  }

  @Override
  public String getUserId() {
    final String userId = MDC.get("userId");
    return StringUtils.isNotBlank(userId) ? userId : "system";
  }

}
