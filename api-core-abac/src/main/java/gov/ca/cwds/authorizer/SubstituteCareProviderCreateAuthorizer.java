package gov.ca.cwds.authorizer;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.SubstituteCareProviderAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-2 Team
 */
public class SubstituteCareProviderCreateAuthorizer extends
    AbstractBaseAuthorizer<SubstituteCareProvider, String> {

  @Inject
  public SubstituteCareProviderCreateAuthorizer(
      DroolsAuthorizationService droolsAuthorizationService,
      SubstituteCareProviderAuthorizationDroolsConfiguration droolsConfiguration) {
    super(droolsAuthorizationService, droolsConfiguration);
  }

  @Override
  protected List<Object> prepareFacts(SubstituteCareProvider instance) {
    return new ArrayList<>();
  }
}
