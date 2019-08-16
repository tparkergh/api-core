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
      List<TraceLogFilter> filters, long delay) {
    this.filters = filters;
    this.timer = new Timer("tracelog");
    timer.schedule(new TraceLogTimerTask(queryDao, accessDao, accessQueue, searchQueue), 45000L,
        delay);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    new CaresSearchQueryParser().parse(json).entrySet().stream().forEach(
        e -> searchQueue.add(new TraceLogSearchEntry(userId, e.getKey().getName(), e.getValue())));
  }

  protected String cleanClassName(Class<?> klass) {
    String ret = klass.getName();
    if (ret.contains("@")) {
      ret = ret.split("@")[1];
    }

    return ret;
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (StringUtils.isNotBlank(userId) && !"anonymous".equals(userId)) {
      final String className = cleanClassName(entity.getClass());
      if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, id))) {
        accessQueue.add(new TraceLogAccessEntry(userId, id, className));
      } else {
        LOGGER.trace("Untraced entity: {}", className);
      }
    }
  }

}
