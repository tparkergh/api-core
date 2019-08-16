package gov.ca.cwds.tracelog.async;

import java.time.LocalDateTime;

import gov.ca.cwds.data.std.ApiObjectIdentity;

public abstract class TraceLogEntry extends ApiObjectIdentity {

  private static final long serialVersionUID = 1L;

  private final String userId;
  private final LocalDateTime moment = LocalDateTime.now();

  public TraceLogEntry(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  public LocalDateTime getMoment() {
    return moment;
  }

}