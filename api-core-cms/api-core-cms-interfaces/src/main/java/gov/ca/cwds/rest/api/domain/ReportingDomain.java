package gov.ca.cwds.rest.api.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

/**
 * @author CWDS API Team
 */
public abstract class ReportingDomain extends DomainObject implements Request, Response {

  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private ArrayList<ErrorMessage> messages = new ArrayList<>();

  @Override
  public ArrayList<ErrorMessage> getMessages() {
    return messages;
  }

  /**
   * @param errorMessage - the error message
   */
  public void addMessage(ErrorMessage errorMessage) {
    if (messages == null) {
      messages = new ArrayList<>();
    }
    messages.add(errorMessage);
  }

  /**
   * @param messages - the error message set
   */
  public void setMessages(ArrayList<ErrorMessage> messages) {
    this.messages = messages;
  }

  @Override
  public boolean hasMessages() {
    return messages != null && !messages.isEmpty();
  }

}
