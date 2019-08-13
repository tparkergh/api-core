package gov.ca.cwds.tracelog;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * Parse CARES Elasticsearch query terms using JSON Path.
 * 
 * @author CWDS API Team
 */
public class CaresSearchQueryParser {

  public enum CaresJsonField {

    BIRTH_DATE("birth date", "$..query_string[?(@.default_field=='date_of_birth')].query"),

    BIRTH_DATE_AS_TEXT("birth date as text",
        "$..query_string[?(@.default_field=='date_of_birth_as_text')].query"),

    RANGE_BIRTH_DATE_GREATER_THAN("birth date, greater than", "$..range.date_of_birth.gte"),

    RANGE_BIRTH_DATE_LESS_THAN("birth date, less than", "$..range.date_of_birth.lte"),

    FIRST_NAME("first name", "$..functions[:1].filter..first_name.query"),

    MIDDLE_NAME("middle name", "$..functions[:1].filter..middle_name.query"),

    LAST_NAME("last name", "$..functions[:1].filter..last_name.query"),

    NAME_SUFFIX("name suffix", "$..name_suffix.query"),

    GENDER_QUERY_STRING("gender", "$..query_string[?(@._name=='1_qs_gender')].query"),

    GENDER_FILTER("gender", "$..match.gender.query"),

    SERVICE_PROVIDER_COUNTY("service provider county", "$..match.sp_county.query"),

    CLIENT_INDEX_NUMBER("client index number",
        "$..functions[:1].filter..client_index_number.query"),

    SSN("SSN", "$..match.ssn.query"),

    CLIENT_ID("client id", "$..['legacy_descriptor.legacy_ui_id_flat'].query"),

    OPEN_CASE_ID("open case id", "$..match.open_case_id.query"),

    ;

    private final String name;
    private final String path;

    private CaresJsonField(String name, String path) {
      this.name = name;
      this.path = path;
    }

    public String getName() {
      return name;
    }

    public String getPath() {
      return path;
    }

  }

  public CaresSearchQueryParser() {}

  public Map<CaresJsonField, String> parse(String json) {
    final Map<CaresJsonField, String> ret = new EnumMap<>(CaresJsonField.class);
    final DocumentContext context = JsonPath.parse(json);

    List<Object> list = null;
    for (CaresJsonField term : CaresJsonField.values()) {
      list = context.read(term.path);

      final String answer = !list.isEmpty() ? ((String) list.get(0)) : null;
      if (StringUtils.isNotBlank(answer)) {
        ret.put(term, answer.trim());
      }
    }

    return ret;
  }

}
