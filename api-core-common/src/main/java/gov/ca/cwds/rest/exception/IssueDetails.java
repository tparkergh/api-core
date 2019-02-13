package gov.ca.cwds.rest.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author CWDS CALS API Team
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IssueDetails implements Serializable {

  private static final long serialVersionUID = -8879466383753472143L;

  public static final String BASE_MESSAGE =
      "There was an error processing your request. It has been logged with unique incident id";

  private String incidentId;

  @NotNull
  private IssueType type;

  @NotNull
  private String userMessage;

  private String code;
  private String technicalMessage;
  private String stackTrace;
  private String causeStackTrace;
  private String property;
  private transient Object invalidValue;

  public IssueType getType() {
    return type;
  }

  public void setType(IssueType type) {
    this.type = type;
  }

  public String getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(String userMessage) {
    this.userMessage = userMessage;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIncidentId() {
    return incidentId;
  }

  public void setIncidentId(String incidentId) {
    this.incidentId = incidentId;
  }

  public String getTechnicalMessage() {
    return technicalMessage;
  }

  public void setTechnicalMessage(String technicalMessage) {
    this.technicalMessage = technicalMessage;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public String getCauseStackTrace() {
    return causeStackTrace;
  }

  public void setCauseStackTrace(String causeStackTrace) {
    this.causeStackTrace = causeStackTrace;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public Object getInvalidValue() {
    return invalidValue;
  }

  public void setInvalidValue(Object invalidValue) {
    this.invalidValue = invalidValue;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getCode()).append(getCode()).append(getProperty()).append(getType()).append(getUserMessage())
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    IssueDetails issueDetails = (IssueDetails) obj;
    EqualsBuilder equalsBuilder = new EqualsBuilder();
    equalsBuilder.append(getCode(), issueDetails.code);
    equalsBuilder.append(getProperty(), issueDetails.property);
    equalsBuilder.append(getType(), issueDetails.getType());
    equalsBuilder.append(getUserMessage(), issueDetails.getUserMessage());
    return equalsBuilder.isEquals();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
