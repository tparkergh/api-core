package gov.ca.cwds.tracelog.delegate;

import java.time.LocalDateTime;
import java.util.Queue;

import com.google.inject.Inject;

import gov.ca.cwds.tracelog.async.TraceLogAccessEntry;
import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.simple.SimpleTraceLogRecordAccessDao;

public class DelegateTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  @Inject
  private TraceLogRecordAccessDao dao = new SimpleTraceLogRecordAccessDao();

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    dao.logRecordAccess(userId, moment, id, entityType);
  }

  public TraceLogRecordAccessDao getDao() {
    return dao;
  }

  public void setDao(TraceLogRecordAccessDao dao) {
    this.dao = dao;
  }

  @Override
  public void logBulkAccess(Queue<TraceLogAccessEntry> accessQueue) {
    dao.logBulkAccess(accessQueue);
  }

}
