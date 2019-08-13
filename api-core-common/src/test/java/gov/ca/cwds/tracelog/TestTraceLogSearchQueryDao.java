package gov.ca.cwds.tracelog;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTraceLogSearchQueryDao implements TraceLogSearchQueryDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestTraceLogSearchQueryDao.class);

  @Override
  public void logSearchQuery(String userId, Date moment, String term, String value) {
    LOGGER.info("fake-save search query: user: {}, when: {}, search term: {}, value: \"{}\"",
        userId, moment, term, value);
  }

}
