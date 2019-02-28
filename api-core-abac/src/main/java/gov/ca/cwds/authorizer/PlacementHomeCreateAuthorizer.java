package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.PlacementHomeAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-2 Team
 */
public class PlacementHomeCreateAuthorizer extends AbstractBaseAuthorizer<PlacementHome, String> {

  @Inject
  public PlacementHomeCreateAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService, PlacementHomeAuthorizationDroolsConfiguration.INSTANCE);
  }

  @Override
  protected List<Object> prepareFacts(PlacementHome instance) {
    return new ArrayList<>();
  }
}
