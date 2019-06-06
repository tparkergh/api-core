package gov.ca.cwds.data.es;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reusable address class for Elasticsearch documents.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchAddress implements Serializable {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("street_address")
  @Size(max = 50)
  @NotNull
  private String streetAddress;

  @JsonProperty("city")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @Size(max = 50)
  private String state;

  @JsonProperty("zip")
  private Integer zip;

  /**
   * Constructor.
   * 
   * @param streetAddress The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   */
  @JsonCreator
  public ElasticSearchAddress(@JsonProperty("street_address") String streetAddress,
      @JsonProperty("city") String city, @JsonProperty("state") String state,
      @JsonProperty("zip") Integer zip) {
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * @return the street Address
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
