package gov.ca.cwds.tracelog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

public class CaresTraceLogServiceImpl implements CaresTraceLogService {

  private final List<CaresTraceLogFilter> filters;

  @Inject
  public CaresTraceLogServiceImpl(Collection<CaresTraceLogFilter> filters) {
    this.filters = new ArrayList<CaresTraceLogFilter>(filters);
  }

  @Override
  public void logSearchQuery(String userId, String json) {
    for (Map.Entry<CaresESQueryParser.CaresJsonField, String> entry : new CaresESQueryParser()
        .parse(json).entrySet()) {
      // TODO: save search query term
    }
  }

  @Override
  public void logRecordAccess(String userId, Object entity, String id) {
    if (filters.stream().anyMatch(f -> f.traceAccess(userId, entity, id))) {
      // TODO: log record access.
    }
  }

}
