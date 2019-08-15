package gov.ca.cwds.tracelog.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.entity.TraceLogSearchQueryLog;

public class TraceLogSearchQueryDaoImpl extends BaseDaoImpl<TraceLogSearchQueryLog>
    implements TraceLogSearchQueryDao {

  @Inject
  public TraceLogSearchQueryDaoImpl(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    create(new TraceLogSearchQueryLog(userId, Timestamp.valueOf(moment), term, value));
  }

}
