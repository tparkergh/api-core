package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class SystemCodeDescriptorTest {

  private Short id = 3225;
  private String description = "some descrption";

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeDescriptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SystemCodeDescriptor target = new SystemCodeDescriptor();
    assertThat(target, notNullValue());
  }

  @Test
  public void testEmptyConstructor() throws Exception {
    SystemCodeDescriptor empty = new SystemCodeDescriptor();
    assertThat(empty.getClass(), is(SystemCodeDescriptor.class));
  }

  @Test
  public void jsonCreatorConstructorTest() throws Exception {

    SystemCodeDescriptor domain = new SystemCodeDescriptor(id, description);

    assertThat(domain.getId(), is(equalTo(id)));
    assertThat(domain.getDescription(), is(equalTo(description)));
  }

}
