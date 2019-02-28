package gov.ca.cwds.test.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Rule;

import io.dropwizard.Configuration;

/**
 * @author CWDS CALS API Team
 */
public abstract class BaseApiTest<T extends Configuration> {

  @SuppressWarnings({"unchecked", "fb-contrib:PCOA_PARTIALLY_CONSTRUCTED_OBJECT_ACCESS"})
  @Rule
  public RestClientTestRule clientTestRule = new RestClientTestRule(getApplication());

  protected abstract BaseDropwizardApplication<T> getApplication();

  public String transformDTOtoJSON(Object o) throws JsonProcessingException {
    return clientTestRule.getMapper().writeValueAsString(o);
  }

  @After
  public void tearDown() {}

}
