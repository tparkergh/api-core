package gov.ca.cwds.tracelog.async;

public final class TraceLogSearchEntry extends TraceLogEntry {

  private static final long serialVersionUID = 1L;

  private final String term;
  private final String value;

  public TraceLogSearchEntry(String userId, String term, String value) {
    super(userId);
    this.term = term;
    this.value = value;
  }

  public String getTerm() {
    return term;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "TraceLogSearchEntry [term=" + term + ", value=" + value + ", user=" + getUserId()
        + ", when=" + getMoment() + "]";
  }

}