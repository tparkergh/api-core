package gov.ca.cwds.test.support;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import gov.ca.cwds.DataSourceName;
import liquibase.exception.LiquibaseException;

/**
 * Created by Alexander Serbin on 1/26/2018.
 */
public class TestApplicationTest extends BaseApiTest<TestConfiguration> {

  @ClassRule
  public static final BaseDropwizardApplication<TestConfiguration> application =
      new BaseDropwizardApplication<>(TestApplication.class, "config/test-api.yml");

  @Override
  protected BaseDropwizardApplication<TestConfiguration> getApplication() {
    return application;
  }

  @BeforeClass
  public static void before() throws LiquibaseException {
    DatabaseHelper.setUpDatabase(application.getConfiguration().getFasDataSourceFactory(),
        DataSourceName.FAS);
    DatabaseHelper.setUpDatabase(application.getConfiguration().getCmsDataSourceFactory(),
        DataSourceName.CWS);
    DatabaseHelper.setUpDatabase(application.getConfiguration().getLisDataSourceFactory(),
        DataSourceName.LIS);
    DatabaseHelper.setUpDatabase(application.getConfiguration().getNsDataSourceFactory(),
        DataSourceName.NS);
    DatabaseHelper.setUpDatabase(application.getConfiguration().getCmsrsDataSourceFactory(),
        DataSourceName.CWSRS);
  }

  @Test
  public void defaultPrincipalTest() throws IOException {
    assertEquals("Ok",
        clientTestRule.target("test").request(MediaType.APPLICATION_JSON).get(String.class));
  }

  @Test
  public void customPrincipalTest() throws IOException {
    final Response response =
        clientTestRule.withSecurityToken("perry-account/custom-perry-account.json")
            .target("test/custom").request(MediaType.APPLICATION_JSON).get();
    assertEquals("Ok", response.readEntity(String.class));
  }

}
