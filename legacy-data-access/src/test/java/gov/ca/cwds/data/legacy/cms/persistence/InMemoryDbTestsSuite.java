package gov.ca.cwds.data.legacy.cms.persistence;

import gov.ca.cwds.data.legacy.cms.dao.CaseDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientOtherEthnicitiesTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientServiceProviderDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.CreditReportHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.CrudDaoImplTest;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.DasHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.DeliveredServiceDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.FCEligibilityDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthReferralDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.HealthScreeningDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.MedicalEligibilityApplicationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.NearFatalityDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.OptimisticLockingTest;
import gov.ca.cwds.data.legacy.cms.dao.ParentalRightsTerminationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.PlacementEpisodeDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.ReferralDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SchoolOriginHistoryDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SexualExploitationTypeDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SpecialEducationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.SpecialProjectReferralDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.StaffPersonDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.TribalAncestryNotificationDaoTest;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDaoTest;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Suite.class)
@PrepareForTest({PrincipalUtils.class})
@PowerMockIgnore({
    "javax.xml.*",
    "org.xml.*",
    "org.w3c.*"
    })
@Suite.SuiteClasses({
    CaseDaoTest.class,
    ClientDaoTest.class,
    ClientOtherEthnicitiesTest.class,
    ClientRelationshipDaoTest.class,
    ClientServiceProviderDaoTest.class,
    CreditReportHistoryDaoTest.class,
    CrudDaoImplTest.class,
    CsecHistoryDaoTest.class,
    DasHistoryDaoTest.class,
    DeliveredServiceDaoTest.class,
    FCEligibilityDaoTest.class,
    HealthInterventionPlanDaoTest.class,
    HealthReferralDaoTest.class,
    HealthScreeningDaoTest.class,
    MedicalEligibilityApplicationDaoTest.class,
    NearFatalityDaoTest.class,
    OptimisticLockingTest.class,
    ParentalRightsTerminationDaoTest.class,
    PaternityDetailDaoTest.class,
    PlacementEpisodeDaoTest.class,
    ReferralDaoTest.class,
    SafetyAlertDaoTest.class,
    SchoolOriginHistoryDaoTest.class,
    SexualExploitationTypeDaoTest.class,
    SpecialEducationDaoTest.class,
    SpecialProjectDaoTest.class,
    SpecialProjectReferralDaoTest.class,
    StaffPersonDaoTest.class,
    TribalAncestryNotificationDaoTest.class,
    TribalMembershipVerificationDaoTest.class
})
public class InMemoryDbTestsSuite {
  @ClassRule
  public static final InMemoryTestResources inMemoryTestResources = InMemoryTestResources.getInstance();
}
