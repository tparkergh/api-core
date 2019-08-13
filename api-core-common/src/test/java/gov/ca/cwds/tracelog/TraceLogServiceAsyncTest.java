package gov.ca.cwds.tracelog;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class TraceLogServiceAsyncTest {

  private TraceLogServiceAsync target;

  @Before
  public void setup() throws Exception {
    final Collection<Class<?>> classes = new ArrayList<>();
    classes.add(TestEntity.class);

    final Collection<TraceLogFilter> filters = new ArrayList<>();
    filters.add(new HibernateTraceLogFilter(classes));
    this.target = new TraceLogServiceAsync(new TestTraceLogSearchQueryDao(),
        new TestTraceLogRecordAccessDao(), filters, 50);
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
    target.logSearchQuery("DVADER", CaresSearchQueryParserTest.JSON_TEST_1);
    Thread.sleep(200L);
  }

}
