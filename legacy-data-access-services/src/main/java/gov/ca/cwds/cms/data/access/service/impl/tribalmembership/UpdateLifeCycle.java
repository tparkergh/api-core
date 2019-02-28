package gov.ca.cwds.cms.data.access.service.impl.tribalmembership;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.TribalMembershipVerificationAwareDto;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;

/**
 * Update lifecycle for Tribals.
 *
 * @author CWDS TPT-3 Team
 * */
public class UpdateLifeCycle extends CreateUpdateLifeCycle {

  @Inject
  public UpdateLifeCycle(
      TribalMembershipVerificationDao tribalMembershipVerificationDao,
      ClientCoreService clientCoreService,
      BusinessValidationService<TribalMembershipVerification, TribalMembershipVerificationAwareDto>
          businessValidationService) {
    super(tribalMembershipVerificationDao, clientCoreService, businessValidationService);
  }

  @Override
  public void beforeDataProcessing(DataAccessBundle bundle) throws DataAccessServicesException {
    super.beforeDataProcessing(bundle);
    createChildTribalForDuplicate(bundle);
  }

  private void createChildTribalForDuplicate(DataAccessBundle bundle) {
    TribalMembershipVerificationAwareDto awareDto =
      (TribalMembershipVerificationAwareDto) bundle.getAwareDto();
    buildChildTribalForDuplicate(awareDto);
  }
}
