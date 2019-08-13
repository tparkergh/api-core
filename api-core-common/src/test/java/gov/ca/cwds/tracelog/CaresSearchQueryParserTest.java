package gov.ca.cwds.tracelog;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.EnumMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.tracelog.CaresSearchQueryParser.CaresJsonField;

public class CaresSearchQueryParserTest {

  private static final String JSON_PRELUDE =
      "{\"size\":\"250\",\"track_scores\":\"true\",\"sort\":[{\"_score\":\"desc\",\"last_name.keyword\":\"asc\",\"first_name.keyword\":\"asc\",\"_uid\":\"desc\"}],\"min_score\":\"2.5\",\"_source\":[\"id\",\"legacy_source_table\",\"first_name\",\"middle_name\",\"last_name\",\"name_suffix\",\"gender\",\"akas\",\"date_of_birth\",\"date_of_death\",\"ssn\",\"languages\",\"races\",\"ethnicity\",\"client_counties\",\"case_status\",\"addresses.id\",\"addresses.effective_start_date\",\"addresses.street_name\",\"addresses.street_number\",\"addresses.city\",\"addresses.county\",\"addresses.state_code\",\"addresses.zip\",\"addresses.type\",\"addresses.legacy_descriptor\",\"addresses.phone_numbers.number\",\"addresses.phone_numbers.type\",\"csec.start_date\",\"csec.end_date\",\"csec.csec_code_id\",\"csec.description\",\"sp_county\",\"sp_phone\",\"legacy_descriptor\",\"highlight\",\"phone_numbers.id\",\"phone_numbers.number\",\"phone_numbers.type\",\"estimated_dob_code\",\"sensitivity_indicator\",\"race_ethnicity\",\"open_case_responsible_agency_code\"],\"highlight\":{\"order\":\"score\",\"number_of_fragments\":\"10\",\"require_field_match\":\"false\",\"fields\":{\"autocomplete_search_bar\":{\"matched_fields\":[\"autocomplete_search_bar\",\"autocomplete_search_bar.phonetic\",\"autocomplete_search_bar.diminutive\"]},\"searchable_date_of_birth\":{}}},";

  private static final String JSON_TEST_1 = JSON_PRELUDE
      + "\"query\":{\"function_score\":{\"query\":{\"bool\":{\"must\":[{\"match\":{\"legacy_descriptor.legacy_table_name\":{\"query\":\"CLIENT_T\",\"_name\":\"q_cli\"}}},{\"range\":{\"date_of_birth\":{\"gte\":\"1971-07-11\",\"lte\":\"1981-09-14\",\"format\":\"yyyy-MM-dd\"}}}],\"filter\":[{\"match\":{\"gender\":{\"query\":\"male\",\"_name\":\"q_gender\"}}},{\"match\":{\"sp_county\":{\"query\":\"los angeles\",\"minimum_should_match\":\"100%\",\"_name\":\"q_county\"}}}]}},\"functions\":[{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"1_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"jorge \",\"_name\":\"1_exact_first\"}}}]}},\"weight\":8192},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"2_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"jorge \",\"_name\":\"2_exact_first\"}}},{\"match\":{\"name_suffix\":{\"query\":\"sr\",\"_name\":\"2_exact_suffix\"}}}]}},\"weight\":16384},{\"filter\":{\"multi_match\":{\"query\":\"hernandez jorge\",\"operator\":\"and\",\"_name\":\"3_multi_aka\",\"fields\":[\"akas.first_name\",\"akas.last_name\"],\"type\":\"cross_fields\"}},\"weight\":4096},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"4_exact_last\"}}},{\"match\":{\"first_name.diminutive\":{\"query\":\"jorge \",\"_name\":\"4_diminutive_first\"}}}]}},\"weight\":2048},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"5_exact_last\"}}},{\"match\":{\"first_name.phonetic\":{\"query\":\"jorge \",\"_name\":\"5_phonetic_first\"}}}]}},\"weight\":1024},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"6_exact_last\"}}},{\"fuzzy\":{\"first_name\":{\"value\":\"jorge \",\"_name\":\"6_fuzzy_first\",\"fuzziness\":\"3\",\"prefix_length\":\"1\",\"max_expansions\":\"50\"}}}]}},\"weight\":512},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"7_exact_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"jorge \",\"minimum_should_match\":\"10%\",\"_name\":\"7_partial_first\"}}}]}},\"weight\":256},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"8_exact_last\"}}}],\"must_not\":[{\"match\":{\"first_name\":{\"query\":\"jorge \",\"_name\":\"8_no_match_first\"}}}]}},\"weight\":128},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"jorge \",\"_name\":\"9a_reverse_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"hernandez\",\"_name\":\"9a_reverse_exact_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"jorge \",\"_name\":\"9b_reverse_partial_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"hernandez\",\"minimum_should_match\":\"25%\",\"_name\":\"9b_reverse_partial_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"hernandez\",\"_name\":\"10_dupe_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"hernandez\",\"_name\":\"10_dupe_exact_first\"}}}]}},\"weight\":32},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"first_name_ngram\":{\"query\":\"jorge \",\"minimum_should_match\":\"10%\",\"_name\":\"11_partial_first\"}}},{\"match\":{\"last_name_ngram\":{\"query\":\"hernandez\",\"minimum_should_match\":\"15%\",\"_name\":\"11_partial_last\"}}}]}},\"weight\":16}],\"score_mode\":\"max\",\"boost_mode\":\"max\"}}}";

  private static final String JSON_TEST_2 = JSON_PRELUDE
      + "\"query\":{\"function_score\":{\"query\":{\"bool\":{\"must\":[{\"match\":{\"legacy_descriptor.legacy_table_name\":{\"query\":\"CLIENT_T\",\"_name\":\"q_cli\"}}},{\"range\":{\"date_of_birth\":{\"gte\":\"1989-06-13\",\"lte\":\"1994-08-12\",\"format\":\"yyyy-MM-dd\"}}}],\"filter\":[{\"match\":{\"gender\":{\"query\":\"female\",\"_name\":\"q_gender\"}}},{\"match\":{\"sp_county\":{\"query\":\"los angeles\",\"minimum_should_match\":\"100%\",\"_name\":\"q_county\"}}}]}},\"functions\":[{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"1_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"maria\",\"_name\":\"1_exact_first\"}}}]}},\"weight\":8192},{\"filter\":{\"multi_match\":{\"query\":\"ortiz maria\",\"operator\":\"and\",\"_name\":\"3_multi_aka\",\"fields\":[\"akas.first_name\",\"akas.last_name\"],\"type\":\"cross_fields\"}},\"weight\":4096},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"4_exact_last\"}}},{\"match\":{\"first_name.diminutive\":{\"query\":\"maria\",\"_name\":\"4_diminutive_first\"}}}]}},\"weight\":2048},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"5_exact_last\"}}},{\"match\":{\"first_name.phonetic\":{\"query\":\"maria\",\"_name\":\"5_phonetic_first\"}}}]}},\"weight\":1024},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"6_exact_last\"}}},{\"fuzzy\":{\"first_name\":{\"value\":\"maria\",\"_name\":\"6_fuzzy_first\",\"fuzziness\":\"3\",\"prefix_length\":\"1\",\"max_expansions\":\"50\"}}}]}},\"weight\":512},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"7_exact_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"maria\",\"minimum_should_match\":\"10%\",\"_name\":\"7_partial_first\"}}}]}},\"weight\":256},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"8_exact_last\"}}}],\"must_not\":[{\"match\":{\"first_name\":{\"query\":\"maria\",\"_name\":\"8_no_match_first\"}}}]}},\"weight\":128},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"maria\",\"_name\":\"9a_reverse_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"ortiz\",\"_name\":\"9a_reverse_exact_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"maria\",\"_name\":\"9b_reverse_partial_last\"}}},{\"match\":{\"first_name_ngram\":{\"query\":\"ortiz\",\"minimum_should_match\":\"25%\",\"_name\":\"9b_reverse_partial_first\"}}}]}},\"weight\":64},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"last_name\":{\"query\":\"ortiz\",\"_name\":\"10_dupe_exact_last\"}}},{\"match\":{\"first_name\":{\"query\":\"ortiz\",\"_name\":\"10_dupe_exact_first\"}}}]}},\"weight\":32},{\"filter\":{\"bool\":{\"must\":[{\"match\":{\"first_name_ngram\":{\"query\":\"maria\",\"minimum_should_match\":\"10%\",\"_name\":\"11_partial_first\"}}},{\"match\":{\"last_name_ngram\":{\"query\":\"ortiz\",\"minimum_should_match\":\"15%\",\"_name\":\"11_partial_last\"}}}]}},\"weight\":16}],\"score_mode\":\"max\",\"boost_mode\":\"max\"}}}";

  private static final String JSON_TEST_3 = JSON_PRELUDE
      + "\"query\":{\"bool\":{\"must\":[{\"match\":{\"legacy_descriptor.legacy_ui_id_flat\":{\"query\":\"1506607661170082075\",\"boost\":\"14\"}}}]}}}";

  private CaresSearchQueryParser target;

  @Before
  public void setup() throws Exception {
    this.target = new CaresSearchQueryParser();
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
  public void parse_1() throws Exception {
    final String json = JSON_TEST_1;

    final Map<CaresJsonField, String> actual = target.parse(json);
    final Map<CaresJsonField, String> expected = new EnumMap<>(CaresJsonField.class);
    expected.put(CaresJsonField.RANGE_BIRTH_DATE_GREATER_THAN, "1971-07-11");
    expected.put(CaresJsonField.RANGE_BIRTH_DATE_LESS_THAN, "1981-09-14");
    expected.put(CaresJsonField.FIRST_NAME, "jorge");
    expected.put(CaresJsonField.LAST_NAME, "hernandez");
    expected.put(CaresJsonField.NAME_SUFFIX, "sr");
    expected.put(CaresJsonField.GENDER_FILTER, "male");
    expected.put(CaresJsonField.SERVICE_PROVIDER_COUNTY, "los angeles");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void parse_2() throws Exception {
    final String json = JSON_TEST_2;

    final Map<CaresJsonField, String> actual = target.parse(json);
    final Map<CaresJsonField, String> expected = new EnumMap<>(CaresJsonField.class);
    expected.put(CaresJsonField.RANGE_BIRTH_DATE_GREATER_THAN, "1989-06-13");
    expected.put(CaresJsonField.RANGE_BIRTH_DATE_LESS_THAN, "1994-08-12");
    expected.put(CaresJsonField.FIRST_NAME, "maria");
    expected.put(CaresJsonField.LAST_NAME, "ortiz");
    expected.put(CaresJsonField.GENDER_FILTER, "female");
    expected.put(CaresJsonField.SERVICE_PROVIDER_COUNTY, "los angeles");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void parse_3() throws Exception {
    final String json = JSON_TEST_3;

    final Map<CaresJsonField, String> actual = target.parse(json);
    final Map<CaresJsonField, String> expected = new EnumMap<>(CaresJsonField.class);
    expected.put(CaresJsonField.CLIENT_ID, "1506607661170082075");
    assertThat(actual, is(equalTo(expected)));
  }

}
