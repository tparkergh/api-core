package gov.ca.cwds.tracelog.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.tracelog.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.entity.TraceLogSearchQueryLog;

public class TraceLogSearchQueryLogDao extends BaseDaoImpl<TraceLogSearchQueryLog>
    implements TraceLogSearchQueryDao {

  @Inject
  public TraceLogSearchQueryLogDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    create(new TraceLogSearchQueryLog(userId, Timestamp.valueOf(moment), term, value));
  }

}
