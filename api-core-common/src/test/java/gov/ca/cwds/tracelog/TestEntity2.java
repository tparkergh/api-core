package gov.ca.cwds.tracelog;

public class TestEntity2 {

  private final String id;
  private final String user;

  public TestEntity2(String id, String user) {
    this.id = id;
    this.user = user;
  }

  public String getId() {
    return id;
  }

  public String getUser() {
    return user;
  }

  @Override
  public String toString() {
    return "TestEntity2 [id=" + id + ", user=" + user + "]";
  }

}
