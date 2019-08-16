package gov.ca.cwds.tracelog.async;

import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.tracelog.core.TraceLogFilter;
import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.core.TraceLogService;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser;

public class TraceLogServiceAsync implements TraceLogService {

  static final Logger LOGGER = LoggerFactory.getLogger(TraceLogServiceAsync.class);

  protected final Queue<TraceLogAccessEntry> accessQueue = new ConcurrentLinkedQueue<>();

  protected final Queue<TraceLogSearchEntry> searchQueue = new ConcurrentLinkedQueue<>();

  protected final Timer timer;

  /**
   * Trace access to tables under watch, not every table in legacy.
   */
  private final List<TraceLogFilter> filters;

  @Inject
  public TraceLogServiceAsync(TraceLogSearchQueryDao queryDao, TraceLogRecordAccessDao accessDao,
      List<TraceLogFilter> filters, long startDelay, long recurringDelay) {
    this.filters = filters;
    this.timer = new Timer("tracelog");
    timer.schedule(new TraceLogTimerTask(queryDao, accessDao, accessQueue, searchQueue), startDelay,
        recurringDelay);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    new CaresSearchQueryParser().parse(json).entrySet().stream().forEach(
        e -> searchQueue.add(new TraceLogSearchEntry(userId, e.getKey().getName(), e.getValue())));
  }

  /**
   * Hibernate creates some funky entity id's.
   * 
   * <p>
   * Well, we probably created them with vararg key generics.
   * </p>
   * 
   * @param id wild crazy Hibernate entity id to clean
   * @return clean entity id that actually looks like an id
   */
  protected String cleanEntityId(String id) {
    String ret = StringUtils.isNotBlank(id) ? id : "new";

    try {
      if (id.contains("@")) {
        ret = id.split("@")[1];
      }
    } catch (Exception e) {
      LOGGER.error("FAILED TO PARSE WEIRD ENTITY ID. id: {}", id, e);
      // Do NOT re-throw. Don't fail a whole bundle because of a one whack id.
    }

    return ret;
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (StringUtils.isNotBlank(userId) && !"anonymous".equals(userId)) {
      final String className = entity.getClass().getName();
      final String realId = cleanEntityId(id);

      if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, realId))) {
        accessQueue.add(new TraceLogAccessEntry(userId, realId, className));
      } else {
        LOGGER.trace("Untraced entity: {}", className);
      }
    }
  }

}
