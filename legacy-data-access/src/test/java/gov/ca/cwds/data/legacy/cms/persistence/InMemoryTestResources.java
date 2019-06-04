package gov.ca.cwds.data.legacy.cms.persistence;

import gov.ca.cwds.test.support.H2Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryTestResources extends ExternalResource {

  private static final String HIBERNATE_CONFIG_PATH = "hibernate.cfg.xml";
  private static final String LIQUIBASE_SCRIPT_PATH = "liquibase/cwscms_database_base_with_lookups.xml";
  private static final String SCHEMA = "CWSCMS";
  private static final String URL =
      "jdbc:h2:mem:cwscms;INIT=create schema if not exists " + SCHEMA +
          "\\;set schema " + SCHEMA + ";DB_CLOSE_DELAY=-1";
  private static final String USER = "";
  private static final String PASSWORD = "";
  private static final String DRIVER_CLASS_NAME = "org.h2.Driver";

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTestResources.class);

  private static int callCount = 0;

  private static InMemoryTestResources INSTANCE;

  public static InMemoryTestResources getInstance() {
    if (callCount == 0) {
      INSTANCE = new InMemoryTestResources();
    }
    return INSTANCE;
  }

  @Override
  protected void before() throws Throwable {
    try {
      if (callCount == 0) {
        setUp();
      }
    } finally {
      callCount++;
    }
  }

  @Override
  protected void after() {
    callCount--;
    if (callCount == 0) {
      tearDown();
    }
  }

  private void setUp() throws Exception {
    createDatabase();
    BaseCwsCmsInMemoryPersistenceTest.sessionFactory = createHibrtnateSessionFactory();

    BaseCwsCmsInMemoryPersistenceTest.dbUnitTester =
        new JdbcDatabaseTester(DRIVER_CLASS_NAME, URL, USER, PASSWORD, SCHEMA);

    BaseCwsCmsInMemoryPersistenceTest.dbUnitConnection =
        BaseCwsCmsInMemoryPersistenceTest.dbUnitTester.getConnection();

    BaseCwsCmsInMemoryPersistenceTest.columnFilter = new DefaultColumnFilter();
    BaseCwsCmsInMemoryPersistenceTest.columnFilter.excludeColumn("LST_UPD_TS");
  }

  private void tearDown() {
    if (BaseCwsCmsInMemoryPersistenceTest.dbUnitConnection != null) {
      try {
        BaseCwsCmsInMemoryPersistenceTest.dbUnitConnection.close();
      } catch (SQLException e) {
        LOGGER.error("Error at closing DbUnit Connection", e);
      }
    }
    if (BaseCwsCmsInMemoryPersistenceTest.sessionFactory != null) {
      BaseCwsCmsInMemoryPersistenceTest.sessionFactory.close();
    }
  }

  private static void createDatabase() throws Exception {
    Class.forName(DRIVER_CLASS_NAME);
    runLiquibaseScript(LIQUIBASE_SCRIPT_PATH);
  }

  private static SessionFactory createHibrtnateSessionFactory() {
    Configuration configuration = new Configuration();
    configuration.configure(HIBERNATE_CONFIG_PATH);
    configuration.setProperty("hibernate.connection.url", URL);
    configuration.setProperty("hibernate.connection.username", USER);
    configuration.setProperty("hibernate.connection.password", PASSWORD);
    configuration.setProperty("hibernate.default_schema", SCHEMA);
    return configuration.buildSessionFactory();
  }

  private static void runLiquibaseScript(String script) throws LiquibaseException {
    try {
      Liquibase liquibase = new Liquibase(script, new ClassLoaderResourceAccessor(), getDatabase());
      liquibase.update((String) null);
    } catch (Exception e) {
      throw new LiquibaseException(e);
    }
  }

  private static Database getDatabase() throws SQLException, DatabaseException {
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    H2Function.createTimestampAlias(connection);
    return DatabaseFactory.getInstance()
        .findCorrectDatabaseImplementation(new JdbcConnection(connection));
  }
}
