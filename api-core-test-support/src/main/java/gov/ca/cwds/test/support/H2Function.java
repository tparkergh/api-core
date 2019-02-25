package gov.ca.cwds.test.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class H2Function {

  private H2Function() {
    //Private constructor. Do nothing.
  }

  public static void createTimestampAlias(final Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute("CREATE ALIAS TIMESTAMP "
        + "FOR \"gov.ca.cwds.test.support.H2Function.timestampFunction\" ");
    }
  }

  public static LocalDateTime timestampFunction(final java.sql.Date date,
    final java.sql.Time time) {
    if (date == null || time == null) {
      return null;
    }
    return LocalDateTime.of(date.toLocalDate(), time.toLocalTime());
  }
}
