package gov.ca.cwds.tracelog.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogService;

public class SimpleTraceLogService implements TraceLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogService.class);

  public SimpleTraceLogService() {
    // no-op
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    LOGGER.info("TRACE LOG: search query: user: {}, json: {}", userId, json);
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    LOGGER.info("TRACE LOG: record access: user: {}, id: {}, type: {}", userId, id,
        entity.getClass().getName());
  }

}
