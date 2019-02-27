package gov.ca.cwds.test.support;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import gov.ca.cwds.rest.services.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import gov.ca.cwds.security.jwt.JwtConfiguration;
import gov.ca.cwds.security.jwt.JwtService;
import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;


public class RestClientTestRule<T extends Configuration> implements TestRule {

  private final static String AUTHORIZED_ACCOUNT_FIXTURE = "default-perry-account.json";
  private final static String DEFAULT_IDENTITY_JSON = fixture(AUTHORIZED_ACCOUNT_FIXTURE);

  private static final Logger LOG = LoggerFactory.getLogger(RestClientTestRule.class);

  private final DropwizardAppRule<T> dropWizardApplication;


  private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[] {new X509TrustManager() {
    @Override
    @SuppressFBWarnings("WEAK_TRUST_MANAGER")
    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0]; //Used only for testing purposes
    }

    @Override
    @SuppressFBWarnings("WEAK_TRUST_MANAGER")
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
      //Used only for testing purposes
    }

    @Override
    @SuppressFBWarnings("WEAK_TRUST_MANAGER")
    public void checkServerTrusted(X509Certificate[] certs, String authType) {
      //Used only for testing purposes
    }
  }};

  private Client client;
  private ObjectMapper mapper;
  private JwtConfiguration jwtConfiguration;
  private String token;

  public RestClientTestRule(DropwizardAppRule<T> dropWizardApplication) {
    this.dropWizardApplication = dropWizardApplication;
    this.token = getDefaultToken();
  }

  private String getDefaultToken() {
    try {
      return generateToken(DEFAULT_IDENTITY_JSON);
    } catch (Exception e) {
      LOG.error("Cannot generate token", e);
      throw new ServiceException(e);
    }
  }

  public RestClientTestRule withSecurityToken(String identityJsonFilePath) throws IOException {
    this.token = generateToken(fixture(identityJsonFilePath));
    return this;
  }

  private String generateToken(String identity) throws IOException {
    JwtConfiguration configuration = getJwtConfiguration();
    JwtService jwtService = new JwtService(configuration);
    return jwtService.generate("id", "subject", identity);
  }

  @SuppressFBWarnings("PATH_TRAVERSAL_IN") //Path cannot be controlled by the user
  private JwtConfiguration getJwtConfiguration() throws IOException {
    if (jwtConfiguration != null) {
      return jwtConfiguration;
    }

    Properties properties = new Properties();
    properties
        .load(getClass().getClassLoader().getResourceAsStream("api-core-test-support-shiro.ini"));

    jwtConfiguration = new JwtConfiguration();
    // JWT
    jwtConfiguration.setTimeout(30);
    jwtConfiguration.setIssuer(properties.getProperty("perryRealm.tokenIssuer"));
    jwtConfiguration.setKeyStore(new JwtConfiguration.KeyStoreConfiguration());
    // KeyStore
    jwtConfiguration.getKeyStore()
        .setPath(new File(properties.getProperty("perryRealm.keyStorePath")).getPath());
    jwtConfiguration.getKeyStore()
        .setPassword(properties.getProperty("perryRealm.keyStorePassword"));
    // Sign/Validate Key
    jwtConfiguration.getKeyStore().setAlias(properties.getProperty("perryRealm.keyStoreAlias"));
    jwtConfiguration.getKeyStore()
        .setKeyPassword(properties.getProperty("perryRealm.keyStoreKeyPassword"));
    // Enc Key
    jwtConfiguration
        .setEncryptionEnabled(Boolean.parseBoolean(properties.getProperty("perryRealm.useEncryption")));
    jwtConfiguration.getKeyStore()
        .setEncKeyPassword(properties.getProperty("perryRealm.encKeyPassword"));
    jwtConfiguration.getKeyStore().setEncAlias(properties.getProperty("perryRealm.encKeyAlias"));
    jwtConfiguration.setEncryptionMethod(properties.getProperty("perryRealm.encryptionMethod"));
    return jwtConfiguration;
  }


  public WebTarget target(String pathInfo) throws IOException {
    String restUrl = getUriString() + pathInfo;
    WebTarget target =
        client.target(restUrl).queryParam("token", token).register(new LoggingFilter());
    token = getDefaultToken();
    return target;
  }

  private String composeUriString() {
    return String.format("http://localhost:%s/", dropWizardApplication.getLocalPort());
  }

  protected String getUriString() {
    return isRemoteMode() ? getRemoteUrl() : composeUriString();
  }

  private boolean isRemoteMode() {
    return StringUtils.isNotEmpty(getRemoteUrl());
  }

  protected String getRemoteUrl() {
    return System.getProperty("remote.app.url");
  }

  public ObjectMapper getMapper() {
    return mapper;
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {

        JerseyClientBuilder clientBuilder =
            new JerseyClientBuilder().property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 20000)
                .hostnameVerifier((hostName, sslSession) -> {
                  // Just ignore host verification for test purposes
                  return true;
                });

        client = clientBuilder.build();
        client.register(new JacksonJsonProvider(mapper));
        client.getSslContext().init(null, TRUST_ALL_CERTS, new SecureRandom());
        mapper = dropWizardApplication.getObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        statement.evaluate();
      }
    };
  }

}

