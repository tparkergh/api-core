package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientResultAuthorizationDroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public class ClientResultReadAuthorizer extends ClientBaseReadAuthorizer {

  public static final String CLIENT_RESULT_READ = "clientResult:read";

  @Inject
  private ClientResultAuthorizationDroolsConfiguration droolsConfiguration;

  @Inject
  public ClientResultReadAuthorizer(
      DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService);
    setDroolsConfiguration(droolsConfiguration);
  }
}
