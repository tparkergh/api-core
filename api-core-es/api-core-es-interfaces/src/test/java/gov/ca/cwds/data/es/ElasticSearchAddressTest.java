package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import gov.ca.cwds.rest.resources.ResourceParamValidator;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author CWDS API Team
 *
 */
@SuppressWarnings("javadoc")
public class ElasticSearchAddressTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {

  }

  @Test
  public void testType() throws Exception {
    assertThat(ElasticSearchAddress.class, notNullValue());
  }

  @Test
  public void testInstantiation() throws Exception {
    String streetAddress = StringUtils.rightPad("A", 50);
    String city = StringUtils.rightPad("B", 50);
    String state = StringUtils.rightPad("C", 50);
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    ResourceParamValidator.validate(target);
    assertThat(target, notNullValue());
  }

  @Test(expected = ConstraintViolationException.class)
  public void test_ctor_size_limits_street() throws Exception {
    String streetAddress = StringUtils.rightPad("A", 51);
    String city = null;
    String state = null;
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    ResourceParamValidator.validate(target);
  }

  @Test(expected = ConstraintViolationException.class)
  public void test_ctor_size_limits_street_null() throws Exception {
    String streetAddress = null;
    String city = null;
    String state = null;
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    ResourceParamValidator.validate(target);
  }

  @Test(expected = ConstraintViolationException.class)
  public void test_ctor_size_limits_city() throws Exception {
    String streetAddress = "";
    String city = StringUtils.rightPad("X", 51);
    String state = null;
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    ResourceParamValidator.validate(target);
  }

  @Test(expected = ConstraintViolationException.class)
  public void test_ctor_size_limits_state() throws Exception {
    String streetAddress = "";
    String city = null;
    String state = StringUtils.rightPad("X", 51);
    Integer zip = null;
    ElasticSearchAddress target = new ElasticSearchAddress(streetAddress, city, state, zip);
    ResourceParamValidator.validate(target);
  }

  @Test
  public void testEquals_Args$Object() throws Exception {
    EqualsVerifier.forClass(ElasticSearchAddress.class).suppress(Warning.NONFINAL_FIELDS).verify();
  }

}
