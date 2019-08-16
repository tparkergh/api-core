package gov.ca.cwds.tracelog.async;

import java.util.Queue;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;

/**
 * Supports asynchronous Trace Log service.
 * 
 * @author CWDS API Team
 */
public final class TraceLogTimerTask extends TimerTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(TraceLogTimerTask.class);

  private final Queue<TraceLogAccessEntry> accessQueue;
  private final Queue<TraceLogSearchEntry> searchQueue;

  private final TraceLogSearchQueryDao searchDao;
  private final TraceLogRecordAccessDao accessDao;

  public TraceLogTimerTask(TraceLogSearchQueryDao searchDao, TraceLogRecordAccessDao accessDao,
      Queue<TraceLogAccessEntry> accessQueue, Queue<TraceLogSearchEntry> searchQueue) {
    this.searchDao = searchDao;
    this.accessDao = accessDao;
    this.accessQueue = accessQueue;
    this.searchQueue = searchQueue;
  }

  /**
   * Process and persist one search query at a time.
   */
  protected void traceSearch() {
    TraceLogSearchEntry se;
    while (!searchQueue.isEmpty() && (se = searchQueue.poll()) != null) {
      LOGGER.debug("Trace Log: save search query: {}", se);
      searchDao.logSearchQuery(se.getUserId(), se.getMoment(), se.getTerm(), se.getValue());
    }
  }

  /**
   * Process and persist one viewed record at a time.
   */
  protected void traceAccess() {
    TraceLogAccessEntry ae;
    while (!accessQueue.isEmpty() && (ae = accessQueue.poll()) != null) {
      LOGGER.debug("Trace Log: save record access: {}", ae);
      accessDao.logRecordAccess(ae.getUserId(), ae.getMoment(), ae.getId(), ae.getType());
    }
  }

  @Override
  public void run() {
    LOGGER.debug("Trace Log: flush queues");
    // traceSearch();
    // traceAccess();

    searchDao.logBulkAccess(searchQueue);
    accessDao.logBulkAccess(accessQueue);
  }

}
