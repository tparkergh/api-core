package gov.ca.cwds.tracelog.delegate;

import java.time.LocalDateTime;

import com.google.inject.Inject;

import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.simple.SimpleTraceLogSearchQueryDao;

public class DelegateTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  @Inject
  private TraceLogSearchQueryDao dao = new SimpleTraceLogSearchQueryDao();

  public DelegateTraceLogSearchQueryDao() {
    // DAO not set
  }

  @Inject
  public DelegateTraceLogSearchQueryDao(TraceLogSearchQueryDao dao) {
    this.dao = dao;
  }

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    dao.logSearchQuery(userId, moment, term, value);
  }

}
