package gov.ca.cwds.tracelog.async;

import java.util.Queue;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.core.TraceLogService;

/**
 * Supports asynchronous Trace Log service.
 * 
 * @author CWDS API Team
 * @see TraceLogService
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

  @Override
  public void run() {
    LOGGER.trace("Trace Log: flush queues");
    searchDao.logBulkAccess(searchQueue);
    accessDao.logBulkAccess(accessQueue);
  }

}
