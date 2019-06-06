package gov.ca.cwds.rest.api.domain.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

/**
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class SystemMetaTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private String logicalTableDsdName = "GVR_ENTC";
  private String shortDescriptionName = "Government Entity Type";
  private String userTableName = "Government Entity";

  @Test
  public void jsonCreatorConstructorTest() throws Exception {
    SystemMeta domain = new SystemMeta(logicalTableDsdName, userTableName, shortDescriptionName);

    assertThat(domain.getLogicalTableDsdName(), is(equalTo(logicalTableDsdName)));
    assertThat(domain.getShortDescriptionName(), is(equalTo(shortDescriptionName)));
    assertThat(domain.getUserTableName(), is(equalTo(userTableName)));
  }

  @Test
  public void serializesToJSON() throws Exception {
    final String expected = MAPPER.writeValueAsString(MAPPER
        .readValue(fixture("fixtures/domain/cms/SystemMeta/valid/valid.json"), SystemMeta.class));

    assertThat(MAPPER.writeValueAsString(validSystemMeta()), is(equalTo(expected)));
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    assertThat(MAPPER.readValue(fixture("fixtures/domain/cms/SystemMeta/valid/valid.json"),
        SystemMeta.class), is(equalTo(validSystemMeta())));
  }

  private SystemMeta validSystemMeta() {
    return new SystemMeta(logicalTableDsdName, shortDescriptionName, userTableName);
  }
}
