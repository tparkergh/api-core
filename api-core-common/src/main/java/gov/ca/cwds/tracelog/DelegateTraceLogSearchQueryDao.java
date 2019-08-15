package gov.ca.cwds.tracelog;

import java.time.LocalDateTime;

import com.google.inject.Inject;

public class DelegateTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  private TraceLogSearchQueryDao dao;

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
