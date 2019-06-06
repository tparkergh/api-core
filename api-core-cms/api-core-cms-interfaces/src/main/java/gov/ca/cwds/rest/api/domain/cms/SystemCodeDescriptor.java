package gov.ca.cwds.rest.api.domain.cms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * System code descriptor
 * 
 * @author CWDS API Team
 */
public class SystemCodeDescriptor extends ApiObjectIdentity implements ApiTypedIdentifier<Short> {

  private static final long serialVersionUID = 6361276510378082085L;

  @JsonProperty("id")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Short id;

  @JsonProperty("description")
  private String description;

  /**
   * Default no-arg constructor
   */
  public SystemCodeDescriptor() {
    // Default ctor
  }

  /**
   * @param id - id
   * @param description - description
   */
  public SystemCodeDescriptor(Short id, String description) {
    super();
    this.id = id;
    this.description = description;
  }

  /**
   * Get id
   * 
   * @return The id
   */
  @Override
  public Short getId() {
    return id;
  }

  /**
   * Set id
   * 
   * @param id The id
   */
  @Override
  public void setId(Short id) {
    this.id = id;
  }

  /**
   * Get description
   * 
   * @return The code description
   */
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
