package gov.ca.cwds.tracelog.simple;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.tracelog.core.TraceLogSearchQueryDao;

public class SimpleTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTraceLogSearchQueryDao.class);

  @Override
  public void logSearchQuery(String userId, LocalDateTime moment, String term, String value) {
    LOGGER.info("save search query: user: {}, when: {}, search term: {}, value: \"{}\"", userId,
        moment, term, value);
  }

}
