package gov.ca.cwds.tracelog.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Queue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.NsSessionFactory;
import gov.ca.cwds.tracelog.async.TraceLogAccessEntry;
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
    Transaction txn = null;
    try (final Session session = getSessionFactory().openSession()) {
      txn = session.beginTransaction();
      create(new TraceLogClientViewLog(userId, Timestamp.valueOf(moment), id, entityType));
      session.getTransaction().commit();
    } catch (Exception e) {
      LOGGER.error("ERROR SAVING SINGLE RECORD ACCESS!", e);
      try {
        if (txn != null) {
          txn.rollback();
        }
      } catch (Exception e2) {
        LOGGER.error("Failed to roll back", e2);
      }
      throw e;
    }
  }

  @Override
  public void logBulkAccess(Queue<TraceLogAccessEntry> accessQueue) {
    if (accessQueue.isEmpty()) {
      return;
    }

    TraceLogAccessEntry ae = null;
    Transaction txn = null;
    try (final Session session = getSessionFactory().openSession()) {
      txn = session.beginTransaction();
      while (!accessQueue.isEmpty() && (ae = accessQueue.poll()) != null) {
        LOGGER.debug("Trace Log: persist record access: {}", ae);
        create(new TraceLogClientViewLog(ae.getUserId(), Timestamp.valueOf(ae.getMoment()),
            ae.getId(), ae.getType()));
      }
      session.getTransaction().commit();
    } catch (Exception e) {
      LOGGER.error("ERROR SAVING BULK RECORD ACCESS!", e);
      try {
        if (txn != null) {
          txn.rollback();
        }
      } catch (Exception e2) {
        LOGGER.error("Failed to roll back", e2);
      }
      throw e;
    }
  }

}
