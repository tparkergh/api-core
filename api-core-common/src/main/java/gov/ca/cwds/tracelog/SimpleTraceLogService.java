package gov.ca.cwds.tracelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTraceLogService implements TraceLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogService.class);

  @Override
  public void logSearchQuery(String userId, String json) {
    LOGGER.debug("TRACE LOG: search query: user: {}, json: {}", userId, json);
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    LOGGER.debug("TRACE LOG: record access: user: {}, id: {}, type: {}", userId, id,
        entity.getClass().getName());
  }

}
