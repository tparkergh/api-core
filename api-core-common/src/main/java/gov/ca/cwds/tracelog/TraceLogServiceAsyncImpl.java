package gov.ca.cwds.tracelog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.inject.Inject;

import gov.ca.cwds.data.std.ApiObjectIdentity;

public class TraceLogServiceAsyncImpl implements TraceLogService {

  public static abstract class TraceLogEntry extends ApiObjectIdentity {

    private static final long serialVersionUID = 1L;

    private final String userId;
    private final Date moment = new Date();

    public TraceLogEntry(String userId) {
      this.userId = userId;
    }

    public String getUserId() {
      return userId;
    }

    public Date getMoment() {
      return moment;
    }

  }

  public static final class TraceLogQueryEntry extends TraceLogEntry {

    private static final long serialVersionUID = 1L;

    private final String term;
    private final String value;

    public TraceLogQueryEntry(String userId, String term, String value) {
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

  }

  public static final class TraceLogTimerTask extends TimerTask {

    private final TraceLogSearchQueryDao queryDao;
    private final TraceLogRecordAccessDao accessDao;

    public TraceLogTimerTask(TraceLogSearchQueryDao queryDao, TraceLogRecordAccessDao accessDao) {
      this.queryDao = queryDao;
      this.accessDao = accessDao;
    }

    @Override
    public void run() {

    }

  }

  private final TraceLogSearchQueryDao queryDao;

  private final TraceLogRecordAccessDao accessDao;

  protected final Queue<TraceLogAccessEntry> accessQueue = new ConcurrentLinkedQueue<>();

  protected final Queue<TraceLogAccessEntry> searchQueue = new ConcurrentLinkedQueue<>();

  protected Timer timer;

  /**
   * Trace access to tables under watch, not every table in legacy.
   */
  private final List<TraceLogFilter> filters;

  @Inject
  public TraceLogServiceAsyncImpl(TraceLogSearchQueryDao queryDao,
      TraceLogRecordAccessDao accessDao, Collection<TraceLogFilter> filters) {
    this.queryDao = queryDao;
    this.accessDao = accessDao;
    this.filters = new ArrayList<TraceLogFilter>(filters);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    for (Map.Entry<CaresSearchQueryParser.CaresJsonField, String> e : new CaresSearchQueryParser()
        .parse(json).entrySet()) {
      queryDao.logSearchQuery(userId, e.getKey().getName(), e.getValue());
    }
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, id))) {
      accessDao.logRecordAccess(userId, entity, id);
    }
  }

}
