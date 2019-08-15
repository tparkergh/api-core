package gov.ca.cwds.tracelog.delegate;

import java.time.LocalDateTime;

import com.google.inject.Inject;

import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.simple.SimpleTraceLogSearchQueryDao;

public class DelegateTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  private TraceLogSearchQueryDao dao = new SimpleTraceLogSearchQueryDao();

  public DelegateTraceLogSearchQueryDao() {
    // DAO not set
  }

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    dao.logSearchQuery(userId, moment, term, value);
  }

  public TraceLogSearchQueryDao getDao() {
    return dao;
  }

  @Inject
  public void setDao(TraceLogSearchQueryDao dao) {
    this.dao = dao;
  }

}
