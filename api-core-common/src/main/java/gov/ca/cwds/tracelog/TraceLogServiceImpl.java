package gov.ca.cwds.tracelog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

public class TraceLogServiceImpl implements TraceLogService {

  private final TraceLogSearchQueryDao queryDao;

  private final List<TraceLogFilter> filters;

  @Inject
  public TraceLogServiceImpl(TraceLogSearchQueryDao queryDao,
      Collection<TraceLogFilter> filters) {
    this.queryDao = queryDao;
    this.filters = new ArrayList<TraceLogFilter>(filters);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    for (Map.Entry<CaresSearchQueryParser.CaresJsonField, String> e : new CaresSearchQueryParser()
        .parse(json).entrySet()) {
      queryDao.saveSearchQuery(userId, e.getKey().getName(), e.getValue());
    }
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, id))) {
      // TODO: log record access.
    }
  }

}
