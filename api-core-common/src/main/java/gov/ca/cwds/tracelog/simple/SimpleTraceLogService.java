package gov.ca.cwds.tracelog.simple;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogService;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser.CaresJsonField;

public class SimpleTraceLogService implements TraceLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogService.class);

  public SimpleTraceLogService() {
    // no-op
  }

  @Override
  public Map<CaresJsonField, String> logSearchQuery(String userId, String json) {
    LOGGER.info("TRACE LOG: search query: user: {}, json: {}", userId, json);
    return new CaresSearchQueryParser().parse(json);
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    LOGGER.info("TRACE LOG: record access: user: {}, id: {}, type: {}", userId, id,
        entity.getClass().getName());
  }

}
