package gov.ca.cwds.tracelog;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTraceLogRecordAccessDao implements TraceLogRecordAccessDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestTraceLogRecordAccessDao.class);

  @Override
  public void logRecordAccess(String userId, Date moment, String id) {
    LOGGER.info("save record access: user: {}, when: {}, id: \"{}\"", userId, moment, id);
  }

}
