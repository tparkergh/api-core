package gov.ca.cwds.tracelog.simple;

import java.time.LocalDateTime;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.async.TraceLogAccessEntry;
import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;

public class SimpleTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogRecordAccessDao.class);

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    LOGGER.info("TRACE LOG: access: user: {}, when: {}, id: {}, entity: {}", userId, moment, id,
        entityType);
  }

  @Override
  public void logBulkAccess(Queue<TraceLogAccessEntry> accessQueue) {
    TraceLogAccessEntry ae;
    while (!accessQueue.isEmpty() && (ae = accessQueue.poll()) != null) {
      LOGGER.debug("Trace Log: save record access: {}", ae);
      logRecordAccess(ae.getUserId(), ae.getMoment(), ae.getId(), ae.getType());
    }
  }

}
