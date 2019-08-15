package gov.ca.cwds.tracelog.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.tracelog.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.entity.TraceLogClientViewLog;

public class TraceLogClientViewLogDao extends BaseDaoImpl<TraceLogClientViewLog>
    implements TraceLogRecordAccessDao {

  @Inject
  public TraceLogClientViewLogDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    create(new TraceLogClientViewLog(userId, Timestamp.valueOf(moment), id, entityType));
  }

}
