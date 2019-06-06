package gov.ca.cwds.data.es;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Safety alert
 * 
 * @author CWDS API Team
 */
public class ElasticSearchSafetyAlert extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 5481552446797727833L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("activation")
  private Activation activation;

  @JsonProperty("deactivation")
  private Deactivation deactivation;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  /**
   * No-argument constructor.
   */
  public ElasticSearchSafetyAlert() {
    // default ctor
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public Activation getActivation() {
    return activation;
  }

  public void setActivation(Activation activation) {
    this.activation = activation;
  }

  public Deactivation getDeactivation() {
    return deactivation;
  }

  public void setDeactivation(Deactivation deactivation) {
    this.deactivation = deactivation;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  /**
   * ==========================================================================
   */

  /**
   * Safety alert activation.
   */
  public static final class Activation extends ApiObjectIdentity {

    private static final long serialVersionUID = 2260969282923941516L;

    @JsonProperty("activation_reason_description")
    private String activationReasonDescription;

    @JsonProperty("activation_reason_id")
    private String activationReasonId;

    @JsonProperty("activation_date")
    private String activationDate;

    @JsonProperty("activation_county")
    private ElasticSearchSystemCode activationCounty;

    @JsonProperty("activation_explanation")
    private String activationExplanation;

    /**
     * Default, no-argument constructor.
     */
    public Activation() {
      // default ctor
    }

    public String getActivationReasonDescription() {
      return activationReasonDescription;
    }

    public void setActivationReasonDescription(String activationReasonDescription) {
      this.activationReasonDescription = activationReasonDescription;
    }

    public String getActivationReasonId() {
      return activationReasonId;
    }

    public void setActivationReasonId(String activationReasonId) {
      this.activationReasonId = activationReasonId;
    }

    public String getActivationDate() {
      return activationDate;
    }

    public void setActivationDate(String activationDate) {
      this.activationDate = activationDate;
    }

    public ElasticSearchSystemCode getActivationCounty() {
      return activationCounty;
    }

    public void setActivationCounty(ElasticSearchSystemCode activationCounty) {
      this.activationCounty = activationCounty;
    }

    public String getActivationExplanation() {
      return activationExplanation;
    }

    public void setActivationExplanation(String activationExplanation) {
      this.activationExplanation = activationExplanation;
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj, false);
    }

  }

  /**
   * ==========================================================================
   */

  /**
   * Safety alert de-activation
   */
  public static final class Deactivation extends ApiObjectIdentity {

    private static final long serialVersionUID = -6039499940899007533L;

    @JsonProperty("deactivation_date")
    private String deactivationDate;

    @JsonProperty("deactivation_county")
    private ElasticSearchSystemCode deactivationCounty;

    @JsonProperty("deactivation_explanation")
    private String deactivationExplanation;

    /**
     * No argument constructor
     */
    public Deactivation() {
      // default ctor.
    }

    public String getDeactivationDate() {
      return deactivationDate;
    }

    public void setDeactivationDate(String deactivationDate) {
      this.deactivationDate = deactivationDate;
    }

    public ElasticSearchSystemCode getDeactivationCounty() {
      return deactivationCounty;
    }

    public void setDeactivationCounty(ElasticSearchSystemCode deactivationCounty) {
      this.deactivationCounty = deactivationCounty;
    }

    public String getDeactivationExplanation() {
      return deactivationExplanation;
    }

    public void setDeactivationExplanation(String deactivationExplanation) {
      this.deactivationExplanation = deactivationExplanation;
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj, false);
    }

  }

}
