package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import gov.ca.cwds.cms.data.access.dao.XaDaoProvider;
import gov.ca.cwds.cms.data.access.dto.ExtendedSCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderCoreService;
import gov.ca.cwds.cms.data.access.service.rules.SubstituteCareProviderDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

/** @author CWDS TPT-2 Team */
public abstract class BaseDocToolRulesSubstituteCareProviderTest extends BaseDocToolRulesTest {

  protected BusinessValidationService businessValidationService;
  private SubstituteCareProviderCoreService scpService;

  ExtendedSCPEntityAwareDTO entityAwareDTO;

  @Before
  public void setUp() {
    businessValidationService = new BusinessValidationService(droolsService);
    SCPEntityAwareDTO scpEntityAwareDTO = new SCPEntityAwareDTO();
    entityAwareDTO = new ExtendedSCPEntityAwareDTO(scpEntityAwareDTO);

    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    entityAwareDTO.setPlacementHomeInformation(placementHomeInformation);

    scpService = new SubstituteCareProviderCoreService(mock(XaDaoProvider.class));

    entityAwareDTO.setEntity(new SubstituteCareProvider());
  }

  void checkSuccess() {
    try {
      businessValidationService.runDataProcessing(
          entityAwareDTO,
          principal,
          SubstituteCareProviderDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          entityAwareDTO, principal, SubstituteCareProviderDroolsConfiguration.INSTANCE);
    } catch (BusinessValidationException e) {
      fail();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  protected void checkSuccess(String ruleCode) {
    try {
      businessValidationService.runDataProcessing(
          entityAwareDTO,
          principal,
          SubstituteCareProviderDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          entityAwareDTO, principal, SubstituteCareProviderDroolsConfiguration.INSTANCE);
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
          .stream()
          .noneMatch(issueDetails -> issueDetails.getCode().equals(ruleCode));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  protected void checkFail(String ruleCode) {
    try {
      businessValidationService.runDataProcessing(
          entityAwareDTO,
          principal,
          SubstituteCareProviderDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          entityAwareDTO, principal, SubstituteCareProviderDroolsConfiguration.INSTANCE);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
              .stream()
              .filter(issueDetails -> issueDetails.getCode().equals(ruleCode))
              .count()
          == 1;
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Override
  public String getPrivilege() {
    return "";
  }
}
