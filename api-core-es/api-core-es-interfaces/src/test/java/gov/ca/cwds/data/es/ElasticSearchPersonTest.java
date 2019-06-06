package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import gov.ca.cwds.data.es.ElasticSearchPerson.ESColumn;
import gov.ca.cwds.data.es.ElasticSearchPerson.ESOptionalCollection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Test;

public class ElasticSearchPersonTest {

  private ElasticSearchPerson target;

  @Before
  public void setup() throws Exception {
    target = new ElasticSearchPerson();
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPerson.class, notNullValue());
  }

    @Test
  public void shouldTrimValues_whenCreated() {
    ElasticSearchPerson person = createElasticSearchPerson();
    assertEquals("first name", person.getFirstName());
    assertEquals("last name", person.getLastName());
    assertEquals("middle name", person.getMiddleName());
    assertEquals("name suffix", person.getNameSuffix());
    assertEquals("date of birth", person.getDateOfBirth());
    assertEquals("ssn", person.getSsn());
    assertEquals("highlight fields", person.getHighlightFields());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void shouldNotTrimValues_whenCreated() {
    ElasticSearchPerson person = createElasticSearchPerson();
    assertEquals(" id ", person.getId());
    assertEquals(" source type ", person.getSourceType());
    assertEquals(" source json ", person.getSourceJson());
  }

  @Test
  public void shouldGenderBeUnknown_whenCreatedWithNotRecognizedGender() {
    ElasticSearchPerson person = createElasticSearchPerson("some gender");
    assertEquals("unknown", person.getGender());
  }

  @Test
  public void shouldGenderBeMale_whenCreatedWithMGender() {
    ElasticSearchPerson person = createElasticSearchPerson(" M");
    assertEquals("male", person.getGender());
  }

  @Test
  public void shouldGenderBeFemale_whenCreatedWithFGender() {
    ElasticSearchPerson person = createElasticSearchPerson("F ");
    assertEquals("female", person.getGender());
  }

  @Test
  public void shouldGenderBeIntersex_whenCreatedWithIGender() {
    ElasticSearchPerson person = createElasticSearchPerson(" I ");
    assertEquals("intersex", person.getGender());
  }

  @Test
  public void shouldDoNotOverrideCollections_whenCreatedWithNullCollection() {
    ElasticSearchPerson person = createElasticSearchPerson();
    assertTrue(person.getAddresses().isEmpty());
    assertTrue(person.getPhones().isEmpty());
    assertTrue(person.getLanguages().isEmpty());
    assertTrue(person.getScreenings().isEmpty());
  }

  private ElasticSearchPerson createElasticSearchPerson(String gender) {
    return new ElasticSearchPerson(" id ", " first name ", " last name ", " middle name ",
      " name suffix ", gender, " date of birth ", " ssn ", " source type ", " source json ", " highlight fields ",
      null, null, null, null);
  }

    @Test
  public void readPerson_Args__String() throws Exception {
    final String json =
        IOUtils.toString(ElasticSearchPersonTest.class.getResourceAsStream("/fixtures/data/es/es_person.json"));
    final ElasticSearchPerson actual = ElasticSearchPerson.readPerson(json);
    assertThat(actual, is(notNullValue())); // if it loads, then it's 80% right.
  }

  @Test
  public void setOf_Args__Class__ObjectArray() throws Exception {
    target.clearOptionalCollections(ESOptionalCollection.NONE);
  }

  @Test
  public void clearOptionalCollections_Args__ObjectArray() throws Exception {
    target.clearOptionalCollections(ESOptionalCollection.REFERRAL);
  }

  @Test
  public void clearOptionalCollections_Args__() throws Exception {
    target.clearOptionalCollections();
  }

  @Test
  public void trim_Args__String() throws Exception {
    String s = null;
    String actual = ElasticSearchPerson.trim(s);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void makeESPerson_Args__SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    ElasticSearchPerson actual = ElasticSearchPerson.makeESPerson(hit);
    assertThat(actual, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceType_Args__() throws Exception {
    String actual = target.getSourceType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceJson_Args__() throws Exception {
    String actual = target.getSourceJson();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceObj_Args__() throws Exception {
    Object actual = target.getSourceObj();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_Args__() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getGender_Args__() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGender_Args__String() throws Exception {
    String gender = null;
    target.setGender(gender);
  }

  @Test
  public void getDateOfBirth_Args__() throws Exception {
    String actual = target.getDateOfBirth();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSearchableDateOfBirth() throws Exception {
    target.setDateOfBirth("1990-09-09");
    String[] expectedDateOfBirths =
      {"09091990", "091990", "1990", "0909",
        "090990", "991990", "9990", "91990",
        "99", "09990", "90990", "0990",
        "0991990", "9091990"};
    String[] actualSearchableDateOfBirth = target.getSearchableDateOfBirth();
    assertThat(Arrays.equals(expectedDateOfBirths, actualSearchableDateOfBirth), is(true));
  }

  @Test
  public void setDateOfBirth_Args__String() throws Exception {
    String dateOfBirth = null;
    target.setDateOfBirth(dateOfBirth);
  }

  @Test
  public void getSsn_Args__() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSsn_Args__String() throws Exception {
    String ssn = null;
    target.setSsn(ssn);
  }

  @Test
  public void getType_Args__() throws Exception {
    String actual = target.getType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setType_Args__String() throws Exception {
    String type = null;
    target.setType(type);
  }

  @Test
  public void getSensitivityIndicator_Args__() throws Exception {
    String actual = target.getSensitivityIndicator();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitivityIndicator__not_sensitive() throws Exception {
    // not sensitive:
    final String sensitivityIndicator = null;
    target.setSensitivityIndicator(sensitivityIndicator);
    final String actual = target.getSensitivityIndicator();
    final String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitivityIndicator__sensitive() throws Exception {
    // sensitive:
    final String sensitivityIndicator = "S";
    target.setSensitivityIndicator(sensitivityIndicator);
    final String actual = target.getSensitivityIndicator();
    final String expected = "S";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitivityIndicator_Args__sealed() throws Exception {
    // sealed:
    final String sensitivityIndicator = "R";
    target.setSensitivityIndicator(sensitivityIndicator);
    final String actual = target.getSensitivityIndicator();
    final String expected = "R";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSource_Args__() throws Exception {
    String actual = target.getSource();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSource_Args__String() throws Exception {
    String source = null;
    target.setSource(source);
  }

  @Test
  public void setId_Args__String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setSourceType_Args__String() throws Exception {
    String sourceType = null;
    target.setSourceType(sourceType);
  }

  @Test
  public void setSourceJson_Args__String() throws Exception {
    String sourceJson = null;
    target.setSourceJson(sourceJson);
  }

  @Test
  public void setSourceObj_Args__Object() throws Exception {
    Object sourceObj = null;
    target.setSourceObj(sourceObj);
  }

  @Test
  public void getHighlightFields_Args__() throws Exception {
    String actual = target.getHighlightFields();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHighlightFields_Args__String() throws Exception {
    String highlightFields = null;
    target.setHighlightFields(highlightFields);
  }

  @Test
  public void toString_Args__() throws Exception {
    String actual = target.toString();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getHighlights_Args__() throws Exception {
    Map<String, String> actual = target.getHighlights();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setHighlights_Args__Map() throws Exception {
    Map<String, String> highlights = new HashMap<>();
    target.setHighlights(highlights);
  }

  @Test
  public void getAddresses_Args__() throws Exception {
    List<ElasticSearchPersonAddress> actual = target.getAddresses();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAddresses_Args__List() throws Exception {
    List<ElasticSearchPersonAddress> addresses = new ArrayList<>();
    target.setAddresses(addresses);
  }

  @Test
  public void getPhones_Args__() throws Exception {
    List<ElasticSearchPersonPhone> actual = target.getPhones();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setPhones_Args__List() throws Exception {
    List<ElasticSearchPersonPhone> phones = new ArrayList<>();
    target.setPhones(phones);
  }

  @Test
  public void getLanguages_Args__() throws Exception {
    List<ElasticSearchPersonLanguage> actual = target.getLanguages();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLanguages_Args__List() throws Exception {
    List<ElasticSearchPersonLanguage> languages = new ArrayList<>();
    target.setLanguages(languages);
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_Args__String() throws Exception {
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setNameSuffix_Args__String() throws Exception {
    String nameSuffix = null;
    target.setNameSuffix(nameSuffix);
  }

  @Test
  public void getScreenings_Args__() throws Exception {
    List<ElasticSearchPersonScreening> actual = target.getScreenings();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setScreenings_Args__List() throws Exception {
    List<ElasticSearchPersonScreening> screenings = new ArrayList<>();
    target.setScreenings(screenings);
  }

  @Test
  public void isUpsert_Args__() throws Exception {
    boolean actual = target.isUpsert();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpsert_Args__boolean() throws Exception {
    boolean upsert = false;
    target.setUpsert(upsert);
  }

  @Test
  public void getReferrals_Args__() throws Exception {
    List<ElasticSearchPersonReferral> actual = target.getReferrals();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setReferrals_Args__List() throws Exception {
    List<ElasticSearchPersonReferral> referrals = new ArrayList<>();
    target.setReferrals(referrals);
  }

  @Test
  public void getRelationships_Args__() throws Exception {
    List<ElasticSearchPersonRelationship> actual = target.getRelationships();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setRelationships_Args__List() throws Exception {
    List<ElasticSearchPersonRelationship> relationships = new ArrayList<>();
    target.setRelationships(relationships);
  }

  @Test
  public void getCases_Args__() throws Exception {
    List<ElasticSearchPersonCase> actual = target.getCases();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setCases_Args__List() throws Exception {
    List<ElasticSearchPersonCase> cases = new ArrayList<>();
    target.setCases(cases);
  }

  @Test
  public void getLegacySourceTable_Args__() throws Exception {
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySourceTable_Args__String() throws Exception {
    String legacySourceTable = null;
    target.setLegacySourceTable(legacySourceTable);
  }

  @Test
  public void getLegacyId_Args__() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_Args__String() throws Exception {
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    Object actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__Object() throws Exception {
    ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void getAkas_Args__() throws Exception {
    List<ElasticSearchPersonAka> actual = target.getAkas();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setAkas_Args__List() throws Exception {
    List<ElasticSearchPersonAka> akas = target.getAkas();
    target.setAkas(akas);
  }

  @Test
  public void setClientCounties_Args__List() throws Exception {
    List<ElasticSearchSystemCode> counties = target.getClientCounties();
    target.setClientCounties(counties);
  }

  @Test
  public void pullCol_Args__Map__Object() throws Exception {
    Map<String, Object> m = new HashMap<>();
    ESColumn f = ESColumn.FIRST_NAME;
    Object actual = ElasticSearchPerson.pullCol(m, f);
    assertThat(actual, notNullValue());
  }

  @Test
  public void equals_Args__Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void readPerson_A$String() throws Exception {
    final String json =
        IOUtils.toString(ElasticSearchPersonTest.class.getResourceAsStream("/fixtures/data/es/es_person.json"));
    Object actual = ElasticSearchPerson.readPerson(json);
    assertThat(actual, is(notNullValue()));
  }

  @Test(expected = IOException.class)
  public void readPerson_A$String_T$IOException() throws Exception {
    String json = "\b\tskdjf*&";
    ElasticSearchPerson.readPerson(json);
  }

  @Test
  public void clearOptionalCollections_A$ObjectArray() throws Exception {
    ESOptionalCollection[] keep = new ESOptionalCollection[] {};
    target.clearOptionalCollections(keep);
  }

  @Test
  public void clearOptionalCollections_A$() throws Exception {
    target.clearOptionalCollections();
  }

  @Test
  public void trim_A$String() throws Exception {
    String s = null;
    String actual = ElasticSearchPerson.trim(s);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void handleHighlights_A$SearchHit$Object() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    ElasticSearchPerson ret = new ElasticSearchPerson();
    ElasticSearchPerson.handleHighlights(hit, ret);
  }

  @Test
  public void makeESPerson_A$SearchHit() throws Exception {
    SearchHit hit = mock(SearchHit.class);
    Object actual = ElasticSearchPerson.makeESPerson(hit);
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getId_A$() throws Exception {
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceType_A$() throws Exception {
    String actual = target.getSourceType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceJson_A$() throws Exception {
    String actual = target.getSourceJson();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSourceObj_A$() throws Exception {
    Object actual = target.getSourceObj();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIndexUpdateTime_A$() throws Exception {
    String actual = target.getIndexUpdateTime();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIndexUpdateTime_A$String() throws Exception {
    String indexUpdateTime = null;
    target.setIndexUpdateTime(indexUpdateTime);
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getGender_A$() throws Exception {
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGender_A$String() throws Exception {
    String gender = null;
    target.setGender(gender);
  }

  @Test
  public void getDateOfBirth_A$() throws Exception {
    String actual = target.getDateOfBirth();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDateOfBirth_A$String() throws Exception {
    String dateOfBirth = null;
    target.setDateOfBirth(dateOfBirth);
  }

  @Test
  public void getDateOfDeath_A$() throws Exception {
    String actual = target.getDateOfDeath();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setDateOfDeath_A$String() throws Exception {
    String dateOfDeath = null;
    target.setDateOfDeath(dateOfDeath);
  }

  @Test
  public void getAutocompleteSearchBar_A$() throws Exception {
    String[] actual = target.getAutocompleteSearchBar();
    String[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDateOfBirthAsText_A$() throws Exception {
    String[] actual = target.getDateOfBirthAsText();
    String[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSearchableDateOfBirth_A$() throws Exception {
    String[] actual = target.getSearchableDateOfBirth();
    String[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSearchableName_A$() throws Exception {
    String[] actual = target.getSearchableName();
    String[] expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_A$() throws Exception {
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSsn_A$String() throws Exception {
    String ssn = null;
    target.setSsn(ssn);
  }

  @Test
  public void getType_A$() throws Exception {
    String actual = target.getType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setType_A$String() throws Exception {
    String type = null;
    target.setType(type);
  }

  @Test
  public void getSensitivityIndicator_A$() throws Exception {
    String actual = target.getSensitivityIndicator();
    String expected = "N";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSensitivityIndicator_A$String() throws Exception {
    String sensitivityIndicator = null;
    target.setSensitivityIndicator(sensitivityIndicator);
  }

  @Test
  public void getIndexNumber_A$() throws Exception {
    String actual = target.getIndexNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIndexNumber_A$String() throws Exception {
    String indexNumber = null;
    target.setIndexNumber(indexNumber);
  }

  @Test
  public void getOpenCaseId_A$() throws Exception {
    String actual = target.getOpenCaseId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setOpenCaseId_A$String() throws Exception {
    String openCaseId = null;
    target.setOpenCaseId(openCaseId);
  }

  @Test
  public void getSource_A$() throws Exception {
    String actual = target.getSource();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSource_A$String() throws Exception {
    String source = null;
    target.setSource(source);
  }

  @Test
  public void setId_A$String() throws Exception {
    String id = null;
    target.setId(id);
  }

  @Test
  public void setSourceType_A$String() throws Exception {
    String sourceType = null;
    target.setSourceType(sourceType);
  }

  @Test
  public void setSourceJson_A$String() throws Exception {
    String sourceJson = null;
    target.setSourceJson(sourceJson);
  }

  @Test
  public void setSourceObj_A$Object() throws Exception {
    Object sourceObj = null;
    target.setSourceObj(sourceObj);
  }

  @Test
  public void getHighlightFields_A$() throws Exception {
    String actual = target.getHighlightFields();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHighlightFields_A$String() throws Exception {
    String highlightFields = null;
    target.setHighlightFields(highlightFields);
  }

  @Test
  public void toString_A$() throws Exception {
    String actual = target.toString();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void hashCode_A$() throws Exception {
    int actual = target.hashCode();
    assertThat(actual, is(not(0)));
  }

  @Test
  public void equals_A$Object() throws Exception {
    Object obj = null;
    boolean actual = target.equals(obj);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHighlights_A$() throws Exception {
    Map<String, String> actual = target.getHighlights();
    Map<String, String> expected = new HashMap<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHighlights_A$Map() throws Exception {
    Map<String, String> highlights = new HashMap<String, String>();
    target.setHighlights(highlights);
  }

  @Test
  public void getAddresses_A$() throws Exception {
    List<ElasticSearchPersonAddress> actual = target.getAddresses();
    List<ElasticSearchPersonAddress> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAddresses_A$List() throws Exception {
    List<ElasticSearchPersonAddress> addresses = new ArrayList<>();
    target.setAddresses(addresses);
  }

  @Test
  public void getPhones_A$() throws Exception {
    List<ElasticSearchPersonPhone> actual = target.getPhones();
    List<ElasticSearchPersonPhone> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhones_A$List() throws Exception {
    List<ElasticSearchPersonPhone> phones = new ArrayList<>();
    target.setPhones(phones);
  }

  @Test
  public void getLanguages_A$() throws Exception {
    List<ElasticSearchPersonLanguage> actual = target.getLanguages();
    List<ElasticSearchPersonLanguage> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLanguages_A$List() throws Exception {
    List<ElasticSearchPersonLanguage> languages = new ArrayList<>();
    target.setLanguages(languages);
  }

  @Test
  public void getMiddleName_A$() throws Exception {
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_A$String() throws Exception {
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void getNameSuffix_A$() throws Exception {
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setNameSuffix_A$String() throws Exception {
    String nameSuffix = null;
    target.setNameSuffix(nameSuffix);
  }

  @Test
  public void getScreenings_A$() throws Exception {
    List<ElasticSearchPersonScreening> actual = target.getScreenings();
    List<ElasticSearchPersonScreening> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setScreenings_A$List() throws Exception {
    List<ElasticSearchPersonScreening> screenings = new ArrayList<>();
    target.setScreenings(screenings);
  }

  @Test
  public void isUpsert_A$() throws Exception {
    boolean actual = target.isUpsert();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpsert_A$boolean() throws Exception {
    boolean upsert = false;
    target.setUpsert(upsert);
  }

  @Test
  public void getReferrals_A$() throws Exception {
    List<ElasticSearchPersonReferral> actual = target.getReferrals();
    List<ElasticSearchPersonReferral> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setReferrals_A$List() throws Exception {
    List<ElasticSearchPersonReferral> referrals = new ArrayList<>();
    target.setReferrals(referrals);
  }

  @Test
  public void getRelationships_A$() throws Exception {
    List<ElasticSearchPersonRelationship> actual = target.getRelationships();
    List<ElasticSearchPersonRelationship> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRelationships_A$List() throws Exception {
    List<ElasticSearchPersonRelationship> relationships = new ArrayList<>();
    target.setRelationships(relationships);
  }

  @Test
  public void getCases_A$() throws Exception {
    List<ElasticSearchPersonCase> actual = target.getCases();
    List<ElasticSearchPersonCase> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCases_A$List() throws Exception {
    List<ElasticSearchPersonCase> cases = new ArrayList<>();
    target.setCases(cases);
  }

  @Test
  public void getSafetyAlerts_A$() throws Exception {
    List<ElasticSearchSafetyAlert> actual = target.getSafetyAlerts();
    List<ElasticSearchSafetyAlert> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setSafetyAlerts_A$List() throws Exception {
    List<ElasticSearchSafetyAlert> safetyAlerts = new ArrayList<>();
    target.setSafetyAlerts(safetyAlerts);
  }

  @Test
  public void getLegacySourceTable_A$() throws Exception {
    String actual = target.getLegacySourceTable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacySourceTable_A$String() throws Exception {
    String legacySourceTable = null;
    target.setLegacySourceTable(legacySourceTable);
  }

  @Test
  public void getLegacyId_A$() throws Exception {
    String actual = target.getLegacyId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLegacyId_A$String() throws Exception {
    String legacyId = null;
    target.setLegacyId(legacyId);
  }

  @Test
  public void getLegacyDescriptor_A$() throws Exception {
    Object actual = target.getLegacyDescriptor();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void setLegacyDescriptor_A$Object() throws Exception {
    ElasticSearchLegacyDescriptor legacyDescriptor = null;
    target.setLegacyDescriptor(legacyDescriptor);
  }

  @Test
  public void getAkas_A$() throws Exception {
    List<ElasticSearchPersonAka> actual = target.getAkas();
    List<ElasticSearchPersonAka> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAkas_A$List() throws Exception {
    List<ElasticSearchPersonAka> akas = new ArrayList<>();
    target.setAkas(akas);
  }

  @Test
  public void getCsecs_A$() throws Exception {
    List<ElasticSearchPersonCsec> actual = target.getCsecs();
    List<ElasticSearchPersonCsec> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCsecs_A$List() throws Exception {
    List<ElasticSearchPersonCsec> csecs = new ArrayList<>();
    target.setCsecs(csecs);
  }

  @Test
  public void getClientCounty_A$() throws Exception {
    Object actual = target.getClientCounty();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientCounty_A$Object() throws Exception {
    ElasticSearchSystemCode clientCounty = null;
    target.setClientCounty(clientCounty);
  }

  @Test
  public void getClientCounties_A$() throws Exception {
    List<ElasticSearchSystemCode> actual = target.getClientCounties();
    List<Object> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientCounties_A$List() throws Exception {
    List<ElasticSearchSystemCode> clientCounties = new ArrayList<>();
    target.setClientCounties(clientCounties);
  }

  @Test
  public void getClientRace_A$() throws Exception {
    Object actual = target.getClientRace();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCleintRace_A$Object() throws Exception {
    ElasticSearchRaceAndEthnicity clientRace = null;
    target.setCleintRace(clientRace);
  }

  private ElasticSearchPerson createElasticSearchPerson() {
    return createElasticSearchPerson(" gender ");
  }
}
