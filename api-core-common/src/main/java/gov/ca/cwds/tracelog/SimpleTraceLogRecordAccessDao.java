package gov.ca.cwds.tracelog;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogRecordAccessDao.class);

  @Override
  public void logRecordAccess(String userId, Date moment, String id, String entityType) {
    LOGGER.info("TRACE LOG: access: user: {}, when: {}, id: {}, entity: {}", userId, moment, id,
        entityType);
  }

}
