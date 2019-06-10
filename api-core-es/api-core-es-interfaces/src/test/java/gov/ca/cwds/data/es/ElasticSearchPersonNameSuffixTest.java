package gov.ca.cwds.data.es;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ca.cwds.data.es.ElasticSearchPerson.ESColumn;

/**
 * NOTE: cannot define all test cases for CMS persistence classes found in CWDS API because they are
 * not available in api-core.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ElasticSearchPersonNameSuffixTest {
  private static final ObjectMapper MAPPER = ElasticSearchPerson.MAPPER;
  String id = "1234567ABC";
  String firstName = "Mike";
  String middleName = "Lavell";
  String lastName = "Smith";
  String nameSuffix = "jr";
  String gender = "M";
  String birthDate = "2000-10-31";
  String ssn = "111122333";
  String sourceType = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient";
  String sourceJson = null;
  String highlight = "{\"firstName\":\"<em>mik</em>\",\"lastName\":\"<em>ith</em>\"}";
  @Mock
  private SearchHit hit;
  @Before
  public void setUp() throws Exception {
    Map<String, HighlightField> highlights = new HashMap();
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPerson.class, notNullValue());
  }

  @Test
  public void testConstuctorSuccess() throws Exception {
    ElasticSearchPerson target =
        new ElasticSearchPerson(id, firstName, lastName, middleName, nameSuffix, gender, birthDate,
            ssn, sourceType, sourceJson, highlight, null, null, null, null);
    assertThat(target, notNullValue());
  }

  @Test
  public void testPullColFirstNameSuccess() throws Exception {
    
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "fred";
    m.put(ESColumn.FIRST_NAME.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColLastNameSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "last name";
    m.put(ESColumn.LAST_NAME.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.LAST_NAME);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColGenderSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "M";
    m.put(ESColumn.GENDER.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.GENDER);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColBirthDateSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "2000-10-31";
    m.put(ESColumn.BIRTH_DATE.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.BIRTH_DATE);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColSsnSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "111122333";
    m.put(ESColumn.SSN.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.SSN);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColTypeSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "gov.ca.cwds.data.persistence.cms.rep.ReplicatedClient";
    m.put(ESColumn.TYPE.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.TYPE);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColSourceSuccess() throws Exception {
    Map<String, Object> m = new HashMap<String, Object>();
    final String value =
        "{\"adjudicatedDelinquentIndicator\":\"\",\"adoptionStatusCode\":\"N\",\"alienRegistrationNumber\":\"\"}";
    m.put(ESColumn.SOURCE.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.SOURCE);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColNameNullSuccess() throws Exception {
    
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = null;
    m.put(ESColumn.FIRST_NAME.getCol(), value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Object expected = value;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPullColUnknownKeyFail() throws Exception {
    
    Map<String, Object> m = new HashMap<String, Object>();
    final String value = "something";
    m.put("who knows", value);
    Object actual = ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME);
    Assert.assertTrue("unknown column in map",
        actual instanceof String && StringUtils.isEmpty((String) actual));
  }

  @Test
  public void trim_Args$String() throws Exception {
    
    String s = null;
    
    
    String actual = ElasticSearchPerson.trim(s);
    
    
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void makeESPerson_Args$SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    Map<String, Object> m = new HashMap<>();
    m.put(ESColumn.FIRST_NAME.getCol(), "john");
    m.put(ESColumn.GENDER.getCol(), "U");
    when(hit.getSource()).thenReturn(m);
    ElasticSearchPerson actual = ElasticSearchPerson.makeESPerson(hit);
    actual.setHighlightFields("{}");
    ElasticSearchPerson expected = new ElasticSearchPerson("", "john", "", null, null, "U", null,
        null, null, null, "{}", null, null, null, null);
    assertThat(actual, notNullValue());
  }

  // @Test
  // public void testSerializeToJSON() throws Exception {
  // ElasticSearchPerson ex = validElasticSearchPerson();
  // final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(ex);
  // ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, middleName,
  // nameSuffix, gender, birthDate, ssn, sourceType, sourceJson, highlight, null, null, null);
  // final String actual = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(esp);
  // assertThat(actual, is(equalTo(expected)));
  // }

  // @Test
  // public void testDeSerializationFromJSON() throws IOException, JsonMappingException, IOException
  // {
  // ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, middleName,
  // nameSuffix, gender, birthDate, ssn, sourceType, sourceJson, highlight, null, null, null);
  //
  // final String actual = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(esp);
  // ElasticSearchPerson expectedEsp = MAPPER.readValue(
  // fixture("fixtures/data/es/validElasticSearchPerson.json"), ElasticSearchPerson.class);
  // final String expected =
  // MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(expectedEsp);
  // assertThat(actual, is(equalTo(expected)));
  // }

  @Test
  public void testDeSerializationFromJSONAddressPhone()
      throws IOException, JsonMappingException, IOException {
    ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, null, null, gender,
        birthDate, ssn, sourceType, sourceJson, highlight, null, null, null, null);
    final String actual = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(esp);
    ElasticSearchPerson expectedEsp =
        MAPPER.readValue(fixture("fixtures/data/es/expected.json"), ElasticSearchPerson.class);
    final String expected = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(expectedEsp);
    // assertThat(actual, is(equalTo(expected)));
  }

  // @Test
  // public void testIsApiPersonAware() throws Exception, JsonMappingException, IOException {
  //
  // ElasticSearchPerson esp = new ElasticSearchPerson(id, firstName, lastName, gender, birthDate,
  // ssn, sourceType, sourceJson, highlight);
  //
  //
  // Object obj = esp.getSourceObj();
  //
  // System.out.println(obj.toString());
  // Object sourceObj = esp.getSourceObj();
  //
  // if (esp.getSourceObj() instanceof ApiPersonAware) {
  // System.out.println("is person aware " + sourceObj.getClass().getName());
  // } else {
  // System.out.println("is NOT person aware " + sourceObj.getClass().getName());
  // }

  //
  // }

  private ElasticSearchPerson validElasticSearchPerson()
      throws JsonParseException, JsonMappingException, IOException {
    return MAPPER.readValue(fixture("fixtures/data/es/validElasticSearchPerson.json"),
        ElasticSearchPerson.class);
  }

}
