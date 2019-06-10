package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Languages.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class ElasticSearchPersonLanguage extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = -3790291114921184095L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("primary")
  private Boolean primary;

  /**
   * Default no-argument constructor
   */
  public ElasticSearchPersonLanguage() {}

  /**
   * Create with parameters.
   * 
   * @param id The id
   * @param name Language description
   * @param primary true if it is primary language.
   */
  public ElasticSearchPersonLanguage(String id, String name, Boolean primary) {
    this.id = id;
    this.name = name;
    this.primary = primary;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get language description
   * 
   * @return The language description
   */
  public String getName() {
    return name;
  }

  /**
   * Set language name
   * 
   * @param name The language name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get if language is person's primary language
   * 
   * @return Is it person's primary language
   */
  public Boolean getPrimary() {
    return primary;
  }

  /**
   * Set if language is person's primary language
   * 
   * @param primary Is it person's primary language
   */
  public void setPrimary(Boolean primary) {
    this.primary = primary;
  }
}
