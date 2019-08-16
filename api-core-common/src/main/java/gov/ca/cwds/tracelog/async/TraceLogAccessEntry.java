package gov.ca.cwds.tracelog.async;

public final class TraceLogAccessEntry extends TraceLogEntry {

  private static final long serialVersionUID = 1L;

  private final String id;
  private final String type;

  public TraceLogAccessEntry(String userId, String id, String type) {
    super(userId);
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "TraceLogAccessEntry [id=" + id + ", type=" + type + ", user=" + getUserId()
        + ", when=" + getMoment() + "]";
  }

}