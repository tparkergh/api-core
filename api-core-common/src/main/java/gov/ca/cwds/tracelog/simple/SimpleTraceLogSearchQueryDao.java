package gov.ca.cwds.tracelog.simple;

import java.time.LocalDateTime;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.async.TraceLogSearchEntry;
import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;

public class SimpleTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogSearchQueryDao.class);

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    LOGGER.info("save search query: user: {}, when: {}, search term: {}, value: \"{}\"", userId,
        moment, term, value);
  }

  @Override
  public void logBulkAccess(Queue<TraceLogSearchEntry> searchQueue) {
    TraceLogSearchEntry se;
    while (!searchQueue.isEmpty() && (se = searchQueue.poll()) != null) {
      LOGGER.info("save search query: user: {}, when: {}, search term: {}, value: \"{}\"",
          se.getUserId(), se.getMoment(), se.getTerm(), se.getValue());
    }
  }

}
