package gov.ca.cwds.tracelog.delegate;

import java.time.LocalDateTime;

import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;

public class DelegateTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private TraceLogRecordAccessDao dao;

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

}
