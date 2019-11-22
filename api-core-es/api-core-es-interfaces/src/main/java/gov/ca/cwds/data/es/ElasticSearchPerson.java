package gov.ca.cwds.data.es;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.ObjectMapperUtils;
import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.rest.api.domain.DomainChef;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Generic "person" class for ElasticSearch results with a String id and optional nested person
 * specialty document.
 * 
 * @author CWDS API Team
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticSearchPerson implements ApiTypedIdentifier<String> {

  /**
   * ElasticSearch field names for document type people.person.
   * 
   * @author CWDS API Team
   * @deprecated retrieves ES document objects for the obsolete Person Search.
   */
  @Deprecated
  public enum ESColumn {
  /**
   * ElasticSearch identifier
   */
  ID("id", String.class, ""),

  /**
   * first name
   */
  FIRST_NAME("first_name", String.class, ""),

  /**
   * middle name
   */
  MIDDLE_NAME("middle_name", String.class, ""),

  /**
   * last name
   */
  LAST_NAME("last_name", String.class, ""),

  /**
   * gender code (M,F,U)
   */
  GENDER("gender", String.class, "U"),

  /**
   * birth date
   */
  BIRTH_DATE("date_of_birth", String.class, null),

  /**
   * Social Security Number
   */
  SSN("ssn", String.class, null),

  /**
   * CWDS API class type, usually a domain class.
   */
  TYPE("type", String.class, null),

  /**
   * Nested JSON from {@link #TYPE} class.
   */
  SOURCE("source", String.class, null);

    /**
     * ElasticSearch column name.
     */
    final String col;

    /**
     * Value's data type as a Java Class.
     */
    final Class<? extends Serializable> klazz;

    /**
     * Default value, if no value is provided.
     */
    final Object defaultVal;

    /**
     * Constructor populates immutable members.
     * 
     * @param col ES column name
     * @param klazz Java Class of value
     * @param defaultVal default value. Must be assignable from {@link #klazz}.
     */
    ESColumn(String col, Class<? extends Serializable> klazz, Object defaultVal) {
      this.col = col;
      this.klazz = klazz;
      this.defaultVal = defaultVal;
    }

    /**
     * @return ElasticSearch column name
     */
    public final String getCol() {
      return col;
    }

    /**
     * @return target data type. Must be assignable from the ElasticSearch result data type.
     */
    public final Class<? extends Serializable> getKlazz() {
      return klazz;
    }

    /**
     * @return default value for target field that lacking a corresponding value in the
     *         ElasticSearch result Map
     */
    public final Object getDefaultVal() {
      return defaultVal;
    }
  }

  /**
   * Enum of optional collections under ES Person, including relationships, referrals, screenings,
   * and cases.
   */
  public enum ESOptionalCollection {

    /**
     * Default. Just what the name says ...
     */
    NONE,

    /**
     * Referral, stored in legacy.
     */
    REFERRAL,

    /**
     * Intake screening, not from legacy.
     */
    SCREENING,

    /**
     * Case.
     */
    CASE,

    /**
     * Person relationships, from legacy.
     */
    RELATIONSHIP,

    /**
     * "Other client names"
     */
    AKA,

    /**
     * Commercially Sexually Exploited Children.
     * <p>
     * Convicted perpetrators should expect a reservation in Hell.
     * </p>
     */
    CSEC,

    /**
     * Safety alerts
     */
    SAFETY_ALERT
  }

  /**
   * Screening assigned social worker.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonSocialWorker extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_assigned_social_worker_id")
    // @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }

    @Override
    @JsonProperty("legacy_assigned_social_worker_last_updated")
    // @Deprecated
    public String getLegacyLastUpdated() {
      return legacyLastUpdated;
    }
  }

  /**
   * Screening staff.
   * 
   * @author CWDS API Team
   */
  @SuppressWarnings("serial")
  public static class ElasticSearchPersonStaff extends ElasticSearchPersonNestedPerson {

    @Override
    @JsonProperty("legacy_staff_id")
    // @Deprecated
    public String getLegacyClientId() {
      return legacyPersonId;
    }
  }

  // =========================
  // PRIVATE STATIC:
  // =========================

  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchPerson.class);

  static gov.ca.cwds.rest.api.domain.cms.SystemCodeCache systemCodes =
      gov.ca.cwds.rest.api.domain.cms.SystemCodeCache.global();

  private static final long serialVersionUID = 1L;

  /**
   * {@link ObjectMapper}, used to deserialize JSON Strings from member {@link #sourceJson} into
   * instances of {@link #sourceType}.
   * 
   * <p>
   * This mapper is thread-safe and reusable across multiple threads, yet any configuration made to
   * it, such as ignoring unknown JSON properties, applies to ALL target class types.
   * </p>
   */
  public static final ObjectMapper MAPPER = ObjectMapperUtils.createObjectMapper();

  // ================
  // MEMBERS:
  // ================

  private static final String DEFAULT_SENSITIVITY_INDICATOR = "N";

  @JsonIgnore
  private transient boolean upsert = false;

  @JsonProperty("index_update_time")
  private String indexUpdateTime;

  @JsonProperty("first_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String firstName;

  @JsonProperty("middle_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String middleName;

  @JsonProperty("last_name")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String lastName;

  @JsonProperty("name_suffix")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String nameSuffix;

  @JsonProperty("date_of_birth")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String dateOfBirth;

  @JsonProperty("date_of_death")
  private String dateOfDeath;

  @JsonProperty("gender")
  private String gender;

  @JsonProperty("ssn")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String ssn;

  @JsonProperty("client_index_number")
  private String indexNumber;

  @JsonProperty("open_case_id")
  private String openCaseId;

  @JsonProperty("open_case_responsible_agency_code")
  private String openCaseResponsibleAgencyCode;

  @JsonProperty("type")
  private transient String type;

  @JsonProperty("sensitivity_indicator")
  private transient String sensitivityIndicator = DEFAULT_SENSITIVITY_INDICATOR;

  /**
   * @deprecated use client_souties
   */
  @Deprecated
  @JsonProperty("client_county")
  private ElasticSearchSystemCode clientCounty;

  @JsonProperty("client_counties")
  private List<ElasticSearchSystemCode> clientCounties;

  @JsonProperty("race_ethnicity")
  private ElasticSearchRaceAndEthnicity clientRace;

  @JsonProperty("source")
  private transient String source;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @JsonProperty("legacy_source_table")
  private String legacySourceTable;

  @JsonProperty("legacy_id")
  private String legacyId;

  @JsonProperty("case_status")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private String caseStatus;

  @JsonProperty("sp_county")
  private String spCounty;

  @JsonProperty("sp_phone")
  private String spPhoneNumber;

  @JsonProperty("estimated_dob_code")
  @JsonInclude
  private String estimatedDobCode;

  @JsonProperty("addresses")
  private List<ElasticSearchPersonAddress> addresses = new ArrayList<>();

  @JsonProperty("phone_numbers")
  private List<ElasticSearchPersonPhone> phones = new ArrayList<>();

  @JsonProperty("languages")
  private List<ElasticSearchPersonLanguage> languages = new ArrayList<>();

  @JsonProperty("screenings")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonScreening> screenings = new ArrayList<>();

  @JsonProperty("referrals")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonReferral> referrals = new ArrayList<>();

  @JsonProperty("relationships")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonRelationship> relationships = new ArrayList<>();

  @JsonProperty("cases")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonCase> cases = new ArrayList<>();

  @JsonProperty("akas")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonAka> akas = new ArrayList<>();

  @JsonProperty("csec")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchPersonCsec> csecs = new ArrayList<>();

  @JsonProperty("safety_alerts")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ElasticSearchSafetyAlert> safetyAlerts = new ArrayList<>();

  private transient Map<String, String> highlights = new LinkedHashMap<>();

  /**
   * The identifier is String in legacy (CMS, mainframe DB2) but Long in new style (NS, PostGreSQL).
   * Therefore, the generic id here is String to accomodate all possibilities without resorting to
   * generics, untyped Object, or collections with heterogenous types. For now,
   * 
   * <p>
   * Java lacks a "union" construct (mutually exclusive child structures), polymorphic return types
   * (though, technically, the JVM can...), and true typed templates.
   * </p>
   */
  @JsonProperty("id")
  private String id;

  /**
   * Original, fully-qualified, persistence-level source class, such
   * "gov.ca.cwds.rest.api.persistence.cms.OtherClientName".
   */
  @JsonIgnore
  private transient String sourceType;

  /**
   * Raw, nested, child document JSON of an API class that implements the CWDS API interface,
   * IPersonAware.
   * 
   * <p>
   * Note that JSON serialization intentionally ignores this member, since it represents the JSON to
   * create a child Object and not the Object itself.
   * </p>
   */
  @JsonIgnore
  private transient String sourceJson;

  /**
   * highlight JSON returned from Elasticsearch with fragments flattened out
   */
  @JsonProperty("highlight")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private transient String highlightFields;

  /**
   * Nested document Object, constructed by deserializing {@link #sourceJson} into an instance of
   * Class type {@link #sourceType}.
   */
  private transient Object sourceObj;

  /**
   * default constructor for Jackson
   */
  public ElasticSearchPerson() {
    // Default, no-op.
  }

  /**
   * Overload constructor, used to accommodate nested document members {@link #sourceType} and
   * {@link #sourceJson}.
   * 
   * @param id identifier
   * @param firstName first name
   * @param lastName last name
   * @param middleName middle name
   * @param nameSuffix name suffix, such as jr or sr
   * @param gender gender code
   * @param birthDate birth date
   * @param ssn SSN without dashes
   * @param sourceType fully-qualified, persistence-level source class
   * @param sourceJson raw, nested child document as JSON
   * @param highlight highlightFields from Elasticsearch
   * @param addresses addresses
   * @param phones phones
   * @param languages languages
   * @param screenings screenings
   */
  @SuppressWarnings("squid:S00107")
  public ElasticSearchPerson(String id, String firstName, String lastName, String middleName,
      String nameSuffix, String gender, String birthDate, String ssn, String sourceType,
      String sourceJson, String highlight, List<ElasticSearchPersonAddress> addresses,
      List<ElasticSearchPersonPhone> phones, List<ElasticSearchPersonLanguage> languages,
      List<ElasticSearchPersonScreening> screenings) {

    // CMS/legacy String id:
    this.id = id;

    // Incorporate Person fields:
    this.firstName = trim(firstName);
    this.lastName = trim(lastName);
    this.middleName = trim(middleName);

    if (StringUtils.isNotBlank(nameSuffix)) {
      this.nameSuffix = trim(nameSuffix);
    }

    this.gender = "unknown";
    if (StringUtils.isNotBlank(gender)) {
      final String comp = gender.trim().toLowerCase();
      if ("m".equals(comp)) {
        this.gender = "male";
      } else if ("f".equals(comp)) {
        this.gender = "female";
      } else if ("i".equals(comp)) {
        this.gender = "intersex";
      }
    }

    this.dateOfBirth = trim(birthDate);
    this.ssn = trim(ssn);

    if (addresses != null && !addresses.isEmpty()) {
      this.addresses = addresses;
    }

    if (phones != null && !phones.isEmpty()) {
      this.phones = phones;
    }

    if (languages != null && !languages.isEmpty()) {
      this.languages = languages;
    }

    if (screenings != null && !screenings.isEmpty()) {
      this.screenings = screenings;
    }

    // Nested document
    this.source = trim(sourceJson);

    // class name
    this.type = trim(sourceType);

    // Nested document:
    this.sourceType = sourceType;
    this.sourceJson = sourceJson;

    // Elasticsearch HighlightFields
    this.highlightFields = trim(highlight);
  }

  /**
   * Convenience method streams variable argument enum entries into a EnumSet.
   * 
   * @param enumClass any enum class
   * @param elem vararg enum entries
   * @return Set of enums
   * @param <V> enum class class
   */
  @SafeVarargs
  public static <V extends Enum<V>> Set<V> setOf(Class<V> enumClass, V... elem) {
    return Arrays.stream(elem).collect(Collectors.toCollection(() -> EnumSet.noneOf(enumClass)));
  }

  /**
   * Read an ElasticSearch document into our ES person object.
   * 
   * @param json document String
   * @return populated ES person object
   * @throws IOException if unable to read
   */
  public static ElasticSearchPerson readPerson(String json) throws IOException {
    return ElasticSearchPerson.MAPPER.readValue(json, ElasticSearchPerson.class);
  }

  /**
   * Clear out optional collections (set to null) so that they are not overwritten by "last run"
   * jobs.
   * 
   * @param keep collections to keep
   */
  public void clearOptionalCollections(ESOptionalCollection... keep) {
    final Set<ESOptionalCollection> keepers = setOf(ESOptionalCollection.class, keep);

    if (!keepers.contains(ESOptionalCollection.REFERRAL)) {
      LOGGER.trace("clear REFERAL");
      this.referrals = null;
    }
    if (!keepers.contains(ESOptionalCollection.SCREENING)) {
      LOGGER.trace("clear SCREENING");
      this.screenings = null;
    }
    if (!keepers.contains(ESOptionalCollection.RELATIONSHIP)) {
      LOGGER.trace("clear SCREENING");
      this.relationships = null;
    }
    if (!keepers.contains(ESOptionalCollection.CASE)) {
      LOGGER.trace("clear CASE");
      this.cases = null;
    }
    if (!keepers.contains(ESOptionalCollection.AKA)) {
      LOGGER.trace("clear AKA");
      this.akas = null;
    }
    if (!keepers.contains(ESOptionalCollection.SAFETY_ALERT)) {
      LOGGER.trace("clear SAFETY_ALERT");
      this.safetyAlerts = null;
    }

    this.highlights = null;
  }

  /**
   * Clear out optional collections so that they are not overwritten by "last run" jobs. This
   * overload clears <strong>all</strong> optional collections.
   */
  public void clearOptionalCollections() {
    clearOptionalCollections(ESOptionalCollection.NONE);
  }

  // =========================
  // PROTECTED STATIC:
  // =========================

  /**
   * Extract field's value from an ElasticSearch result {@link Map} (key: field name), using the
   * field's ES column name and data type.
   * 
   * @param <T> expected data type
   * @param m ES result map
   * @param f field to extract
   * @return field value as specified type T
   */
  @SuppressWarnings("unchecked")
  protected static <T extends Serializable> T pullCol(final Map<String, Object> m, ESColumn f) {
    return (T) f.klazz.cast(m.getOrDefault(f.col, f.defaultVal));
  }

  /**
   * Trim excess whitespace from ElasticSearch results. Non-null input of all whitespace returns
   * empty String, null input returns null.
   * 
   * @param s String to trim
   * @return String trimmed of outer whitespace or null if input is null
   */
  protected static String trim(String s) {
    return s != null ? s.trim() : null;
  }

  // =========================
  // PUBLIC STATIC:
  // =========================

  /**
   * Getter for CMS system code cache.
   * 
   * @return reference to CMS system code cache
   */
  public static gov.ca.cwds.rest.api.domain.cms.SystemCodeCache getSystemCodes() {
    return ElasticSearchPerson.systemCodes;
  }

  protected static void handleHighlights(final SearchHit hit, final ElasticSearchPerson ret) {
    // ElasticSearch Java API returns map of highlighted fields
    final Map<String, HighlightField> h = hit.getHighlightFields();
    final Map<String, String> highlightValues = new LinkedHashMap<>();

    // Go through the HighlightFields returned from ES deal with fragments and create map.
    for (final Map.Entry<String, HighlightField> entry : h.entrySet()) {
      String highlightValue;
      final HighlightField highlightField = entry.getValue();
      final Text[] fragments = highlightField.fragments();
      if (fragments != null && fragments.length != 0) {
        final String[] texts = new String[fragments.length];
        for (int i = 0; i < fragments.length; i++) {
          texts[i] = fragments[i].string().trim();
        }
        highlightValue = StringUtils.join(texts, "...");
        highlightValues.put(DomainChef.camelCaseToLowerUnderscore(highlightField.getName()),
            highlightValue);
      }
    }

    // Update this ElasticSearchPerson property with the highlighted text.
    String highLights = "";
    try {
      highLights = MAPPER.writeValueAsString(highlightValues);
    } catch (JsonProcessingException e) {
      throw new ServiceException("ElasticSearch Person error: Failed serialize map to JSON "
          + ret.getSourceType() + ", doc id=" + ret.getId(), e);
    }
    ret.setHighlightFields(highLights);
    ret.setHighlights(highlightValues);
  }

  /**
   * Produce an ElasticSearchPerson domain instance from native ElasticSearch {@link SearchHit}.
   * Parse JSON results and populate associated fields. ElasticSearch Java API returns an overly
   * broad type of {@code Map<String,Object>}. The enum "knows" how to extract columns of a given
   * type.
   * 
   * <p>
   * <strong>Classloader Note:</strong> When running in an application server, the root classloader
   * may not know of our domain/persistence class, and so we look it up using the current thread's
   * classloader, like so:
   * </p>
   * 
   * <blockquote>
   * {@code Class.forName("some.nested.class", false, Thread.currentThread().getContextClassLoader())}
   * </blockquote>
   * 
   * @param hit search result
   * @return populated domain-level ES object
   * @deprecated retrieves ES document objects for the obsolete Person Search.
   * @see #pullCol(Map, ESColumn)
   */
  @Deprecated
  public static ElasticSearchPerson makeESPerson(final SearchHit hit) {
    final Map<String, Object> m = hit.getSource();

    ElasticSearchPerson ret =
        new ElasticSearchPerson(ElasticSearchPerson.<String>pullCol(m, ESColumn.ID),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.FIRST_NAME),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.LAST_NAME), null, null,
            ElasticSearchPerson.<String>pullCol(m, ESColumn.GENDER),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.BIRTH_DATE),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.SSN),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.TYPE),
            ElasticSearchPerson.<String>pullCol(m, ESColumn.SOURCE), "", null, null, null, null);

    if (!StringUtils.isBlank(ret.getSourceType()) && !StringUtils.isBlank(ret.getSourceJson())) {
      try {
        // DELIVERED: STORY #137216799:
        // Tech debt: reverse compatibility with existing ElasticSearch documents.
        if (ret.getSourceType().startsWith("gov.ca.cwds.rest.api.")) {
          LOGGER.warn("LEGACY CLASS IN ES! class={}, id={}", ret.getSourceType(), ret.getId());
        }

        // Remove excess whitespace.
        // No job should store excess whitespace in ElasticSearch!
        final String json = ret.getSourceJson().replaceAll("\\s+\",", "\",");

        // Dynamically instantiate the domain class specified by "type" and load from JSON.
        // Note: When running in an application server, the app server's root classloader may not
        // know of our domain/persistence class, but the current thread's classloader should.
        final Object obj = MAPPER.readValue(json, Class.forName(ret.getSourceType(), false,
            Thread.currentThread().getContextClassLoader()));
        ret.sourceObj = obj;
        handleHighlights(hit, ret);
      } catch (Exception e) {
        throw new ServiceException(
            "ElasticSearch Person error: " + e.getMessage() + ", ES person id=" + ret.getId(), e);
      }
    }

    return ret;
  }

  // =========================
  // ACCESSORS:
  // =========================

  /**
   * See comments on {@link #id}.
   * 
   * @return the id
   */
  @Override
  @JsonProperty
  public String getId() {
    return id;
  }

  /**
   * See comments on {@link #sourceType}.
   * 
   * @return the fully-qualified source persistence class
   */
  @JsonIgnore
  public String getSourceType() {
    return sourceType;
  }

  /**
   * See comments on {@link #sourceJson}. JSON streaming intentionally ignores this field.
   * 
   * @return the raw JSON of nested person document, if any
   * @see #sourceType
   */
  @JsonIgnore
  public String getSourceJson() {
    return sourceJson;
  }

  /**
   * See comments on {@link #sourceObj}.
   * 
   * @return an instance of the Object represented by {@link #sourceType} and {@link #sourceJson}.
   * @see #sourceType
   */
  @JsonIgnore
  public Object getSourceObj() {
    return sourceObj;
  }

  /**
   * Get time stamp of last update to this document
   * 
   * @return Time stamp of last update to this document
   */
  public String getIndexUpdateTime() {
    return indexUpdateTime;
  }

  /**
   * Set time stamp of last update to this document
   * 
   * @param indexUpdateTime Time stamp of last update to this document
   */
  public void setIndexUpdateTime(String indexUpdateTime) {
    this.indexUpdateTime = indexUpdateTime;
  }

  /**
   * Getter for first name.
   * 
   * @return first name
   */
  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  /**
   * Setter for first name
   * 
   * @param firstName first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Getter for last name.
   * 
   * @return last name
   */
  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }

  /**
   * Setter for last name.
   * 
   * @param lastName last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Getter for gender.
   * 
   * @return gender
   */
  @JsonProperty("gender")
  public String getGender() {
    return gender;
  }

  /**
   * Setter for gender.
   * 
   * @param gender gender code (not SYS_ID)
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Getter for date of birth.
   * 
   * @return date of birth
   */
  @JsonProperty("date_of_birth")
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Setter for dateOfBirth.
   * 
   * @param dateOfBirth birth date
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = trim(dateOfBirth);
  }

  /**
   * Getter for date of death.
   * 
   * @return date of death
   */
  @JsonProperty("date_of_death")
  public String getDateOfDeath() {
    return dateOfDeath;
  }

  /**
   * Setter for dateOfDeath.
   * 
   * @param dateOfDeath death date
   */
  public void setDateOfDeath(String dateOfDeath) {
    this.dateOfDeath = trim(dateOfDeath);
  }

  /**
   * Getter for autocomplete_search_bar.
   *
   * @return autocomplete search bar
   */
  @JsonProperty("autocomplete_search_bar")
  public String[] getAutocompleteSearchBar() {
    String[] autocompleteSearchBar = null;
    Set<String> autocompleteSearchValues = new HashSet<>();
    if (StringUtils.isNotBlank(this.ssn)) {
      autocompleteSearchValues.add(this.ssn);
    }
    if (StringUtils.isNotBlank(this.dateOfBirth)) {
      autocompleteSearchValues.addAll(getDobValues(this.dateOfBirth));
    }
    Set<String> allNames = getAllNames();
    if (!allNames.isEmpty()) {
      autocompleteSearchValues.addAll(getAllNames());
    }
    if (!autocompleteSearchValues.isEmpty()) {
      autocompleteSearchBar =
          autocompleteSearchValues.toArray(new String[autocompleteSearchValues.size()]);
    }
    return autocompleteSearchBar;
  }

  /**
   * Getter for "date of birth as text.
   *
   * @return date of birth as text
   */
  @JsonProperty("date_of_birth_as_text")
  public String[] getDateOfBirthAsText() {
    String[] dobAsText = null;
    if (StringUtils.isNotBlank(this.dateOfBirth)) {
      Set<String> dobValues = getDobValues(this.dateOfBirth);
      dobAsText = dobValues.toArray(new String[dobValues.size()]);
    }
    return dobAsText;
  }

  /**
   * Getter for searchable date of birth.
   *
   * @return searchable date of birth
   */
  @JsonProperty("searchable_date_of_birth")
  public String[] getSearchableDateOfBirth() {
    String[] searchableDob = null;
    if (StringUtils.isNotBlank(this.dateOfBirth)) {
      Set<String> dobValues = getDobValues(this.dateOfBirth);
      searchableDob = dobValues.toArray(new String[dobValues.size()]);
    }
    return searchableDob;
  }

  /**
   * Getter for searchable name.
   *
   * @return Searchable name
   */
  @JsonProperty("searchable_name")
  public String[] getSearchableName() {
    String[] searchableName = null;
    Set<String> names = getAllNames();
    if (!names.isEmpty()) {
      searchableName = names.toArray(new String[names.size()]);
    }
    return searchableName;
  }

  /**
   * Getter for SSN.
   * 
   * @return SSN
   */
  @JsonProperty("ssn")
  public String getSsn() {
    return ssn;
  }

  /**
   * Setter for SSN
   * 
   * @param ssn the SSN
   */
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  /**
   * Getter for nested document type.
   * 
   * @return nested document type as an API class
   */
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  /**
   * Setter for nested document type.
   * 
   * @param type nested document type as an API class
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Get sensitivity code (indicator)
   * 
   * @return The sensitivity code (indicator)
   */
  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  /**
   * Set the sensitivity code (indicator)
   * 
   * @param sensitivityIndicator The sensitivity code (indicator)
   */
  public void setSensitivityIndicator(String sensitivityIndicator) {
    if (StringUtils.isBlank(sensitivityIndicator)) {
      this.sensitivityIndicator = DEFAULT_SENSITIVITY_INDICATOR;
    } else {
      this.sensitivityIndicator = sensitivityIndicator;
    }
  }

  /**
   * Get client index number
   * 
   * @return The client index number
   */
  public String getIndexNumber() {
    return indexNumber;
  }

  /**
   * Set the clien index number
   * 
   * @param indexNumber The client index number
   */
  public void setIndexNumber(String indexNumber) {
    this.indexNumber = indexNumber;
  }

  /**
   * Get client's open case id
   * 
   * @return open case id
   */
  public String getOpenCaseId() {
    return openCaseId;
  }

  /**
   * Set client's open case id
   * 
   * @param openCaseId Open case id
   */
  public void setOpenCaseId(String openCaseId) {
    this.openCaseId = openCaseId;
  }

  /**
   * Get responsible agency code for open case.
   * 
   * @return Responsible agency code for open case.
   */
  public String getOpenCaseResponsibleAgencyCode() {
    return openCaseResponsibleAgencyCode;
  }

  /**
   * Set responsible agency code for open case.
   * 
   * @param openCaseResponsibleAgencyCode Responsible agency code for open case.
   */
  public void setOpenCaseResponsibleAgencyCode(String openCaseResponsibleAgencyCode) {
    this.openCaseResponsibleAgencyCode = openCaseResponsibleAgencyCode;
  }

  /**
   * Getter for source.
   * 
   * @return source object JSON
   */
  @JsonProperty("source")
  public String getSource() {
    return source;
  }

  /**
   * Setter for source.
   * 
   * @param source JSON source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Setter for id.
   * 
   * @param id legacy String identifier
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Setter for API class type of nested JSON.
   * 
   * @param sourceType fully-qualified API class of nested JSON
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  /**
   * Setter for source JSON.
   * 
   * @param sourceJson JSON of the nested API class
   */
  public void setSourceJson(String sourceJson) {
    this.sourceJson = sourceJson;
  }

  /**
   * Setter for source object.
   * 
   * @param sourceObj live CWDS API class instance
   */
  public void setSourceObj(Object sourceObj) {
    this.sourceObj = sourceObj;
  }

  /**
   * Getter for highlighFields
   * 
   * @return JSON for highlightFields returned from Elasticsearch
   */
  @JsonProperty("highlight")
  public String getHighlightFields() {
    return highlightFields;
  }

  /**
   * Setter for highlightFields
   * 
   * @param highlightFields JSON of Elasticsearch highlightFields with flattened fragments
   */
  public void setHighlightFields(String highlightFields) {
    this.highlightFields = highlightFields;
  }


  private Set<String> getAllNames() {
    Set<String> names = new HashSet<>();

    if (StringUtils.isNotBlank(this.firstName)) {
      names.add(this.firstName);
    }

    if (StringUtils.isNotBlank(this.lastName)) {
      names.add(this.lastName);
    }

    if (StringUtils.isNotBlank(this.middleName)) {
      names.add(this.middleName);
    }

    if (StringUtils.isNotBlank(this.nameSuffix)) {
      names.add(this.nameSuffix);
    }

    if (this.akas != null) {
      for (ElasticSearchPersonAka aka : this.akas) {
        if (StringUtils.isNotBlank(aka.getFirstName())) {
          names.add(aka.getFirstName());
        }

        if (StringUtils.isNotBlank(aka.getLastName())) {
          names.add(aka.getLastName());
        }
      }
    }
    return names;
  }

  private Set<String> getDobValues(String dateOfBirth) {
    Set<String> dobValues = new LinkedHashSet<>();
    Date date = DomainChef.uncookDateString(dateOfBirth);

    // With zeros, e.g. 01/09/1995
    DateFormat df = new SimpleDateFormat("MMddyyyy");
    String mmddyyyyDob = df.format(date);
    dobValues.add(mmddyyyyDob);

    // Month and Year only, e.g. 09/1995
    df = new SimpleDateFormat("MMyyyy");
    String mmyyyyDob = df.format(date);
    dobValues.add(mmyyyyDob);

    // Year only, e.g. 1995
    df = new SimpleDateFormat("yyyy");
    String yyyyDob = df.format(date);
    dobValues.add(yyyyDob);

    // Month and Day, e.g. 01/09
    df = new SimpleDateFormat("MMdd");
    String mmddDob = df.format(date);
    dobValues.add(mmddDob);

    // Month and Day and Year, e.g. 01/09/90
    df = new SimpleDateFormat("MMddyy");
    String mmddyyDob = df.format(date);
    dobValues.add(mmddyyDob);

    // Remove leading zeros, e.g. 1/9/1995
    df = new SimpleDateFormat("Mdyyyy");
    String mdyyyyDob = df.format(date);
    if (!mmddyyyyDob.equals(mdyyyyDob)) {
      dobValues.add(mdyyyyDob);

      // Month and Day and Year without zeros, e.g. 9/3/90
      df = new SimpleDateFormat("Mdyy");
      String mdyyDob = df.format(date);
      dobValues.add(mdyyDob);

      // Month and year only without zeros, e.g. 9/1995
      df = new SimpleDateFormat("Myyyy");
      String myyyyDob = df.format(date);
      dobValues.add(myyyyDob);

      // Month and Day without zeros, e.g. 1/9
      df = new SimpleDateFormat("Md");
      String mdDob = df.format(date);
      dobValues.add(mdDob);

      // Day without zeros, e.g. 01/9/16
      df = new SimpleDateFormat("MMdyy");
      String mmdyyDob = df.format(date);
      dobValues.add(mmdyyDob);

      // Month without zeros, e.g. 1/09/16
      df = new SimpleDateFormat("Mddyy");
      String mddyyDob = df.format(date);
      dobValues.add(mddyyDob);

      // Month and Year, e.g. 01/16
      df = new SimpleDateFormat("MMyy");
      String mmyyDob = df.format(date);
      dobValues.add(mmyyDob);

      // Day without zeros, e.g. 01/9/2016
      df = new SimpleDateFormat("MMdyyyy");
      String mmdyyyyDob = df.format(date);
      dobValues.add(mmdyyyyDob);

      // Month without zeros, e.g. 1/09/2016
      df = new SimpleDateFormat("Mddyyyy");
      String mddyyyyDob = df.format(date);
      dobValues.add(mddyyyyDob);
    }
    return dobValues;
  }

  @Override
  public String toString() {
    String ret = "";

    try {
      ret = ElasticSearchPerson.MAPPER.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      final String msg = MessageFormat.format("UNABLE TO SERIALIZE JSON!! {}", e.getMessage());
      LOGGER.error(msg, e);
      throw new ApiElasticSearchException(msg, e);
    }

    return ret;
  }

  /**
   * Omit transient fields, such as source object and source JSON. Only compare meaningful fields
   * like "id" or "first name."
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * Omit transient fields, such as source object and source JSON. Only compare meaningful fields
   * like "id" or "first name." Even "source type" should not affect the comparison, only "real"
   * fields should. Therefore, if all non-transient fields match, then the objects match, despite
   * their source.
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  @JsonIgnore
  public Map<String, String> getHighlights() {
    return highlights;
  }

  public void setHighlights(Map<String, String> highlights) {
    this.highlights = highlights;
  }

  @JsonIgnore
  public List<ElasticSearchPersonAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<ElasticSearchPersonAddress> addresses) {
    this.addresses = addresses;
  }

  @JsonIgnore
  public List<ElasticSearchPersonPhone> getPhones() {
    return phones;
  }

  public void setPhones(List<ElasticSearchPersonPhone> phones) {
    this.phones = phones;
  }

  @JsonIgnore
  public List<ElasticSearchPersonLanguage> getLanguages() {
    return languages;
  }

  public void setLanguages(List<ElasticSearchPersonLanguage> languages) {
    this.languages = languages;
  }

  @JsonIgnore
  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @JsonIgnore
  public String getNameSuffix() {
    return nameSuffix;
  }

  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  @JsonIgnore
  public List<ElasticSearchPersonScreening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<ElasticSearchPersonScreening> screenings) {
    this.screenings = screenings;
  }

  @JsonIgnore
  public boolean isUpsert() {
    return upsert;
  }

  public void setUpsert(boolean upsert) {
    this.upsert = upsert;
  }

  public List<ElasticSearchPersonReferral> getReferrals() {
    return referrals;
  }

  public void setReferrals(List<ElasticSearchPersonReferral> referrals) {
    this.referrals = referrals;
  }

  public List<ElasticSearchPersonRelationship> getRelationships() {
    return relationships;
  }

  public void setRelationships(List<ElasticSearchPersonRelationship> relationships) {
    this.relationships = relationships;
  }

  public List<ElasticSearchPersonCase> getCases() {
    return cases;
  }

  public void setCases(List<ElasticSearchPersonCase> cases) {
    this.cases = cases;
  }

  public List<ElasticSearchSafetyAlert> getSafetyAlerts() {
    return safetyAlerts;
  }

  public void setSafetyAlerts(List<ElasticSearchSafetyAlert> safetyAlerts) {
    this.safetyAlerts = safetyAlerts;
  }

  // @Deprecated
  public String getLegacySourceTable() {
    return legacySourceTable;
  }

  // @Deprecated
  public void setLegacySourceTable(String legacySourceTable) {
    this.legacySourceTable = legacySourceTable;
  }

  // @Deprecated
  public String getLegacyId() {
    return legacyId;
  }

  // @Deprecated
  public void setLegacyId(String legacyId) {
    this.legacyId = legacyId;
  }

  /**
   * Get legacy descriptor
   * 
   * @return The legacy descriptor
   */
  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  /**
   * Set legacy descriptor
   * 
   * @param legacyDescriptor The legacy descriptor
   */
  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public List<ElasticSearchPersonAka> getAkas() {
    return akas;
  }

  public void setAkas(List<ElasticSearchPersonAka> akas) {
    this.akas = akas;
  }

  /**
   * Get CSEC history
   *
   * @return CSES history
   */
  public List<ElasticSearchPersonCsec> getCsecs() {
    return csecs;
  }

  /**
   * Set CSEC history
   *
   * @param csecs CSEC history
   */
  public void setCsecs(List<ElasticSearchPersonCsec> csecs) {
    this.csecs = csecs;
  }

  /**
   * Get client county
   * 
   * @return The client county
   * @deprecated Use {@link #getClientCounties()}
   */
  @Deprecated
  public ElasticSearchSystemCode getClientCounty() {
    return clientCounty;
  }

  /**
   * Set client county
   * 
   * @param clientCounty The client county
   * @deprecated Use {@link #setClientCounties(List)}
   */
  @Deprecated
  public void setClientCounty(ElasticSearchSystemCode clientCounty) {
    this.clientCounty = clientCounty;
  }

  /**
   * Get the client counties
   * 
   * @return The client counties
   */
  public List<ElasticSearchSystemCode> getClientCounties() {
    return clientCounties;
  }

  /**
   * Set the client counties
   * 
   * @param clientCounties The client counties
   */
  public void setClientCounties(List<ElasticSearchSystemCode> clientCounties) {
    this.clientCounties = clientCounties;
  }

  /**
   * Get client race
   * 
   * @return The client race
   */
  public ElasticSearchRaceAndEthnicity getClientRace() {
    return clientRace;
  }

  /**
   * Set client race
   * 
   * @param clientRace The client race
   */
  public void setCleintRace(ElasticSearchRaceAndEthnicity clientRace) {
    this.clientRace = clientRace;
  }

  public void setClientRace(ElasticSearchRaceAndEthnicity clientRace) {
    this.clientRace = clientRace;
  }

  /**
   * Getter for first name ngram.
   * 
   * @return first name ngram
   */
  @JsonProperty("first_name_ngram")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String getFirstNameNgram() {
    return this.getFirstName();
  }

  /**
   * Getter for middle name ngram.
   * 
   * @return middle name ngram
   */
  @JsonProperty("middle_name_ngram")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String getMiddleNameNgram() {
    return this.getMiddleName();
  }

  /**
   * Getter for last name ngram.
   * 
   * @return last name ngram
   */
  @JsonProperty("last_name_ngram")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String getLastNameNgram() {
    return this.getLastName();
  }

  /**
   * Getter for name suffix ngram.
   * 
   * @return name suffix ngram
   */
  @JsonProperty("name_suffix_ngram")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public String getNameSuffixNgram() {
    return this.getNameSuffix();
  }

  public String getCaseStatus() {
    return caseStatus;
  }

  public void setCaseStatus(String caseStatus) {
    this.caseStatus = caseStatus;
  }

  public String getSpCounty() {
    return spCounty;
  }

  public void setSpCounty(String spCounty) {
    this.spCounty = spCounty;
  }

  public String getSpPhoneNumber() {
    return spPhoneNumber;
  }

  public void setSpPhoneNumber(String spPhoneNumber) {
    this.spPhoneNumber = spPhoneNumber;
  }

  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  public void setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
  }

}
