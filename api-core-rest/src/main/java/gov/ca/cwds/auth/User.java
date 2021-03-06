package gov.ca.cwds.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.shiro.subject.Subject;

import gov.ca.cwds.data.std.ApiMarker;

/**
 * Represents a logged-in user with a mainframe RACF id.
 * 
 * @author CWDS API Team
 */
public class User implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private final String racf;
  private transient Subject subject;

  /**
   * Constructor.
   * 
   * @param racf the RACF mainframe id
   * @param subject Shiro subject
   */
  public User(String racf, Subject subject) {
    this.racf = checkNotNull(racf, "RACF ID cannot be null.");
    this.subject = checkNotNull(subject, "Subject cannot be null.");
  }

  /**
   * @return the RACF mainframe id
   */
  public String getRacf() {
    return racf;
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

}
