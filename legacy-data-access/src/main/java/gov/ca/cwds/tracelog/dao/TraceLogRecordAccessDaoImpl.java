package gov.ca.cwds.tracelog.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.entity.TraceLogClientViewLog;

public class TraceLogRecordAccessDaoImpl extends BaseDaoImpl<TraceLogClientViewLog>
    implements TraceLogRecordAccessDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(TraceLogRecordAccessDaoImpl.class);

  @Inject
  public TraceLogRecordAccessDaoImpl(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType) {
    LOGGER.info("Trace Log, log access: user: {}, entity: {}, id: {}", userId, entityType, id);
    try (final Session session = getSessionFactory().openSession()) {
      session.beginTransaction();
      create(new TraceLogClientViewLog(userId, Timestamp.valueOf(moment), id, entityType));
      session.getTransaction().commit();
    } catch (Exception e) {
      try {
        getSessionFactory().getCurrentSession().getTransaction().rollback();
      } catch (Exception e2) {
        LOGGER.error("Failed to roll back", e2);
      }
    }
  }

}
