package gov.ca.cwds.tracelog.async;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.tracelog.core.TraceLogFilter;
import gov.ca.cwds.tracelog.core.TraceLogRecordAccessDao;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;
import gov.ca.cwds.tracelog.core.TraceLogService;
import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser;

public class TraceLogServiceAsync implements TraceLogService {

  static final Logger LOGGER = LoggerFactory.getLogger(TraceLogServiceAsync.class);

  public static abstract class TraceLogEntry extends ApiObjectIdentity {

    private static final long serialVersionUID = 1L;

    private final String userId;
    private final LocalDateTime moment = LocalDateTime.now();

    public TraceLogEntry(String userId) {
      this.userId = userId;
    }

    public String getUserId() {
      return userId;
    }

    public LocalDateTime getMoment() {
      return moment;
    }

  }

  public static final class TraceLogSearchEntry extends TraceLogEntry {

    private static final long serialVersionUID = 1L;

    private final String term;
    private final String value;

    public TraceLogSearchEntry(String userId, String term, String value) {
      super(userId);
      this.term = term;
      this.value = value;
    }

    public String getTerm() {
      return term;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "TraceLogSearchEntry [term=" + term + ", value=" + value + ", user=" + getUserId()
          + ", when=" + getMoment() + "]";
    }

  }

  public static final class TraceLogAccessEntry extends TraceLogEntry {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String type;

    public TraceLogAccessEntry(String userId, String id, String type) {
      super(userId);
      this.id = id;
      this.type = type;
    }

    public String getId() {
      return id;
    }

    public String getType() {
      return type;
    }

    @Override
    public String toString() {
      return "TraceLogAccessEntry [id=" + id + ", type=" + type + ", user=" + getUserId()
          + ", when=" + getMoment() + "]";
    }

  }

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
    timer.schedule(new TraceLogTimerTask(queryDao, accessDao, accessQueue, searchQueue), 30L,
        delay);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    new CaresSearchQueryParser().parse(json).entrySet().stream().forEach(
        e -> searchQueue.add(new TraceLogSearchEntry(userId, e.getKey().getName(), e.getValue())));
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (StringUtils.isNotBlank(userId) && !"anonymous".equals(userId)) {
      final String className = entity.getClass().getName();
      if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, id))) {
        accessQueue.add(new TraceLogAccessEntry(userId, id, className));
      } else {
        LOGGER.trace("Untraced entity: {}", className);
      }
    }
  }

}
