package gov.ca.cwds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Static functions for ObjectMapper creation and configuration.
 * 
 * @author CWDS API Team
 */
public class ObjectMapperUtils {

  /**
   * Create a new ObjectMapper
   * 
   * @return ObjectMapper
   */
  public static ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    configureObjectMapper(objectMapper);
    return objectMapper;
  }

  /**
   * Configure given ObjectMapper.
   * 
   * {@link ObjectMapper}, used to deserialize JSON Strings from source into instances of chosen
   * type.
   * 
   * <p>
   * This mapper is thread-safe and reusable across multiple threads, yet any configuration made to
   * it, such as ignoring unknown JSON properties, applies to ALL target class types.
   * </p>
   * 
   * <p>
   * Relax strict constraints regarding unknown JSON properties, since API classes may change over
   * time, and not all classes emit version information in JSON.
   * </p>
   *
   * <p>
   * Fixes bug #140710983: Bug: Person Search converts dates to GMT. Set default time zone to JVM
   * default, which must match the database and Elasticsearch server.
   * </p>
   * 
   * @param objectMapper ObjectMapper to configure
   */
  public static void configureObjectMapper(ObjectMapper objectMapper) {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    // The mainframe DB2 database runs in PST, and so we must too.
    final TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

    final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    fmt.setTimeZone(tz);
    objectMapper.setDateFormat(fmt);
    objectMapper.setTimeZone(tz);
    objectMapper.registerModule(new JodaModule());
  }

  private ObjectMapperUtils() { // NOSONAR
    // Hidden constructor.
  }

}
