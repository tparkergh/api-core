package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.PlacementHomeReadAuthDroolsConfig;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;

import java.util.ArrayList;
import java.util.List;

/**
 * Placement Home read Authorizer.
 *
 * @author CWDS TPT-2 Team
 */
public class PlacementHomeResultReadAuthorizer extends
    AbstractBaseAuthorizer<PlacementHome, String> {

  public static final String PLACEMENT_HOME_RESULT_READ = "placementHome:readResult";
  public static final String PLACEMENT_HOME_RESULT_READ_OBJECT =
      PLACEMENT_HOME_RESULT_READ + ":placementHome";

  @Inject
  public PlacementHomeResultReadAuthorizer(DroolsAuthorizationService droolsAuthorizationService) {
    super(droolsAuthorizationService, PlacementHomeReadAuthDroolsConfig.INSTANCE);
  }

  @Override
  protected List<Object> prepareFacts(PlacementHome instance) {
    return new ArrayList<>();
  }
}
