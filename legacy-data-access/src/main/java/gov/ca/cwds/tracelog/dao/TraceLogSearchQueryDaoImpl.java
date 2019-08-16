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
import gov.ca.cwds.tracelog.async.TraceLogSearchEntry;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.entity.TraceLogSearchQueryLog;

public class TraceLogSearchQueryDaoImpl extends BaseDaoImpl<TraceLogSearchQueryLog>
    implements TraceLogSearchQueryDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(TraceLogSearchQueryDaoImpl.class);

  @Inject
  public TraceLogSearchQueryDaoImpl(@NsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    LOGGER.info("Trace Log: search query: user: {}, term: {}, id: {}", userId, term, value);
    Transaction txn = null;
    try (final Session session = getSessionFactory().openSession()) {
      txn = session.beginTransaction();
      create(new TraceLogSearchQueryLog(userId, Timestamp.valueOf(moment), term, value));
      session.getTransaction().commit();
    } catch (Exception e) {
      LOGGER.error("ERROR SAVING SINGLE SEARCH QUERY!", e);
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
  public void logBulkAccess(Queue<TraceLogSearchEntry> searchQueue) {
    if (searchQueue.isEmpty()) {
      return;
    }

    TraceLogSearchEntry ae = null;
    Transaction txn = null;
    try (final Session session = getSessionFactory().openSession()) {
      txn = session.beginTransaction();
      while (!searchQueue.isEmpty() && (ae = searchQueue.poll()) != null) {
        LOGGER.debug("Trace Log: persist search query: {}", ae);
        create(new TraceLogSearchQueryLog(ae.getUserId(), Timestamp.valueOf(ae.getMoment()),
            ae.getTerm(), ae.getValue()));
      }
      session.flush();
      session.clear();
      txn.commit();
    } catch (Exception e) {
      LOGGER.error("ERROR SAVING BULK SEARCH QUERY!", e);
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
