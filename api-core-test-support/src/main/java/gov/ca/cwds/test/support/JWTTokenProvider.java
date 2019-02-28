package gov.ca.cwds.test.support;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.ca.cwds.security.jwt.JwtConfiguration;
import gov.ca.cwds.security.jwt.JwtService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author CWDS TPT-2 Team
 */
public class JWTTokenProvider implements TokenProvider<JsonIdentityAuthParams> {

  @Override
  public String doGetToken(JsonIdentityAuthParams config) {
    JwtConfiguration configuration = null;

    configuration = getJwtConfiguration();
    JwtService jwtService = new JwtService(configuration);
    return jwtService.generate("id", "subject", config.getIdentityJson());
  }

  @SuppressFBWarnings("PATH_TRAVERSAL_IN") //Path cannot be controlled by the user
  private JwtConfiguration getJwtConfiguration() {
    Properties properties = new Properties();

    try(FileInputStream shiroIni = new FileInputStream("config/shiro.ini")){
      properties.load(shiroIni);

      JwtConfiguration configuration = new JwtConfiguration();
      //JWT
      configuration.setTimeout(30);
      configuration.setIssuer(properties.getProperty("perryRealm.tokenIssuer"));
      configuration.setKeyStore(new JwtConfiguration.KeyStoreConfiguration());
      //KeyStore
      configuration.getKeyStore()
        .setPath(new File(properties.getProperty("perryRealm.keyStorePath")).getPath());
      configuration.getKeyStore().setPassword(properties.getProperty("perryRealm.keyStorePassword"));
      //Sign/Validate Key
      configuration.getKeyStore().setAlias(properties.getProperty("perryRealm.keyStoreAlias"));
      configuration.getKeyStore()
        .setKeyPassword(properties.getProperty("perryRealm.keyStoreKeyPassword"));
      //Enc Key
      configuration
        .setEncryptionEnabled(Boolean.parseBoolean(properties.getProperty("perryRealm.useEncryption")));
      configuration.getKeyStore()
        .setEncKeyPassword(properties.getProperty("perryRealm.encKeyPassword"));
      configuration.getKeyStore().setEncAlias(properties.getProperty("perryRealm.encKeyAlias"));
      configuration.setEncryptionMethod(properties.getProperty("perryRealm.encryptionMethod"));
      return configuration;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
