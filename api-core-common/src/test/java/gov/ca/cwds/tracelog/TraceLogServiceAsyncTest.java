package gov.ca.cwds.tracelog;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TraceLogServiceAsyncTest {

  private static final String USER_ID = "DVADER";

  private TraceLogSearchQueryDao searchDao;
  private SimpleTraceLogRecordAccessDao accessDao;
  private TraceLogServiceAsync target;

  @Before
  public void setup() throws Exception {
    searchDao = mock(TraceLogSearchQueryDao.class);
    accessDao = mock(SimpleTraceLogRecordAccessDao.class);

    final List<Class<?>> classes = new ArrayList<>();
    classes.add(TestEntity.class);

    final List<TraceLogFilter> filters = new ArrayList<>();
    filters.add(new HibernateTraceLogFilter(classes));

    this.target = new TraceLogServiceAsync(searchDao, accessDao, filters, 50L);
  }

  @Test
  public void type() throws Exception {
    assertThat(CaresSearchQueryParser.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void logSearchQuery() throws Exception {
    target.logSearchQuery(USER_ID, CaresSearchQueryParserTest.JSON_TEST_1);
    Thread.sleep(200L);

    verify(searchDao, times(7)).logSearchQuery(any(String.class), any(LocalDateTime.class),
        any(String.class), any(String.class));
    assertTrue("SEARCH QUEUE NOT EMPTY!", target.searchQueue.isEmpty());
  }

  @Test
  public void logRecordAccess_Traced() throws Exception {
    final TestEntity entity = new TestEntity("1234567890", USER_ID);
    target.logRecordAccess(USER_ID, entity, entity.getId());
    Thread.sleep(200L);

    verify(accessDao, times(1)).logRecordAccess(any(String.class), any(LocalDateTime.class),
        any(String.class), any(String.class));
    assertTrue("ACCESS QUEUE NOT EMPTY!", target.accessQueue.isEmpty());
  }

  @Test
  public void logRecordAccess_NotTraced() throws Exception {
    final TestEntity2 entity = new TestEntity2("1234567890", USER_ID);
    target.logRecordAccess(USER_ID, entity, entity.getId());
    Thread.sleep(200L);

    verify(accessDao, times(0)).logRecordAccess(any(String.class), any(LocalDateTime.class),
        any(String.class), any(String.class));
    assertTrue("ACCESS QUEUE NOT EMPTY!", target.accessQueue.isEmpty());
  }

}
