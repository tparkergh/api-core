package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Standard Elasticsearch document type for CWS/CMS system code.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchSystemCode extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 4396490810924002909L;

  @JsonIgnore
  private String id;

  @JsonIgnore
  private String description;

  /**
   * Default no-arg constructor
   */
  public ElasticSearchSystemCode() {
    // Default ctor
    super();
  }

  public ElasticSearchSystemCode(String id, String description) {
    super();
    this.id = id;
    this.description = description;
  }

  /**
   * Copy constructor.
   * 
   * @param old object to copy from
   */
  public ElasticSearchSystemCode(ElasticSearchSystemCode old) {
    super();
    this.id = old.id;
    this.description = old.description;
  }

  /**
   * Get id
   * 
   * @return The id
   */
  @Override
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  /**
   * Set id
   * 
   * @param id The id
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get description
   * 
   * @return The code description
   */
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  /**
   * Set description
   * 
   * @param description The code description
   */
  public void setDescription(String description) {
    this.description = description;
  }

}
