package gov.ca.cwds.tracelog.simple;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;

public class SimpleTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogRecordAccessDao.class);

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    LOGGER.info("TRACE LOG: access: user: {}, when: {}, id: {}, entity: {}", userId, moment, id,
        entityType);
  }

}
