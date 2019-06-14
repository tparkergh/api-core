package gov.ca.cwds.data.persistence;

import java.util.Optional;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;

/**
 * @author CWDS CALS API Team
 */
public class XADataSourceFactory extends DataSourceFactory {

  public static final int BORROW_CONNECTION_TIMEOUT = 30;
  public static final int MAINTENANCE_INTERVAL = 60;
  public static final int MAX_IDLE_TIME = 30;
  public static final int MAX_LIFE_TIME = 1800;
  public static final int REAP_TIMEOUT = 300;

  @NotNull
  private String xaDataSourceClassName;

  private Properties xaProperties;

  @JsonProperty
  public String getXaDataSourceClassName() {
    return xaDataSourceClassName;
  }

  public void setXaDataSourceClassName(String xaDataSourceClassName) {
    this.xaDataSourceClassName = xaDataSourceClassName;
  }

  @JsonProperty
  public Properties getXaProperties() {
    return xaProperties;
  }

  public void setXaProperties(Properties xaProperties) {
    this.xaProperties = xaProperties;
  }

  @Override
  public ManagedDataSource build(MetricRegistry metricRegistry, String name) {
    final AtomikosPooledManagedDataSource ds = new AtomikosPooledManagedDataSource();
    ds.setUniqueResourceName(name);
    ds.setXaDataSourceClassName(xaDataSourceClassName);

    final Properties props = new Properties();
    Optional.ofNullable(getUser()).ifPresent(user -> props.put("user", user));
    Optional.ofNullable(getPassword()).ifPresent(password -> props.put("password", password));
    Optional.ofNullable(getXaProperties()).ifPresent(props::putAll);

    ds.setXaProperties(props);
    ds.setXaProperties(props);
    ds.setMinPoolSize(getMinSize());
    ds.setMaxPoolSize(getMaxSize());
    ds.setBorrowConnectionTimeout(BORROW_CONNECTION_TIMEOUT);
    ds.setMaintenanceInterval(MAINTENANCE_INTERVAL);
    ds.setMaxIdleTime(MAX_IDLE_TIME);
    ds.setMaxLifetime(MAX_LIFE_TIME);
    ds.setReapTimeout(REAP_TIMEOUT);
    ds.setTestQuery(getValidationQuery());

    // Init on start ManagedDataSource.start
    return ds;
  }
}
