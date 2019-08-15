package gov.ca.cwds.tracelog.delegate;

import java.time.LocalDateTime;

import com.google.inject.Inject;

import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.simple.SimpleTraceLogRecordAccessDao;

public class DelegateTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private TraceLogRecordAccessDao dao = new SimpleTraceLogRecordAccessDao();

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    dao.logRecordAccess(userId, moment, id, entityType);
  }

  public TraceLogRecordAccessDao getDao() {
    return dao;
  }

  @Inject
  public void setDao(TraceLogRecordAccessDao dao) {
    this.dao = dao;
  }

}
