package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.PhoneticPrimaryNameCode.PLACEMENT_HOME_ADDRESS;
import static gov.ca.cwds.cms.data.access.Constants.SsaName3StoredProcedureCrudOperationCode.INSERT_OPERATION_CODE;
import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;
import static gov.ca.cwds.cms.data.access.utils.ParametersValidator.checkNotPersisted;
import static gov.ca.cwds.security.utils.PrincipalUtils.getStaffPersonId;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.PlacementHomeResultReadAuthorizer;
import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.Constants.PhoneticSearchTables;
import gov.ca.cwds.cms.data.access.dao.NonXaDaoProvider;
import gov.ca.cwds.cms.data.access.dao.XaDaoProvider;
import gov.ca.cwds.cms.data.access.dto.AppAndLicHistoryAwareDTO;
import gov.ca.cwds.cms.data.access.dto.CLCEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherAdultInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherChildInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.ExternalInterfaceMapper;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.BackgroundCheckDao;
import gov.ca.cwds.data.legacy.cms.dao.CountyOwnershipDao;
import gov.ca.cwds.data.legacy.cms.dao.EmergencyContactDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.ExternalInterfaceDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherAdultsInPlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherChildrenInPlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.OutOfStateCheckDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementFacilityTypeHistoryDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeProfileDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeUcDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck;
import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import gov.ca.cwds.data.legacy.cms.entity.PlacementFacilityTypeHistory;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeProfile;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeUc;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * Service for create/update/find PlacementHome with business validation and data processing.
 *
 * @author CWDS TPT-3 Team
 */
public class PlacementHomeCoreService
    extends DataAccessServiceBase<PlacementHomeDao, PlacementHome, PlacementHomeEntityAwareDTO> {

  @Inject
  private BusinessValidationService businessValidationService;

  @Inject
  private CountyOwnershipMapper countyOwnershipMapper;

  @Inject
  private ExternalInterfaceMapper externalInterfaceMapper;

  @Inject
  private SubstituteCareProviderCoreService substituteCareProviderService;

  @Inject
  private CountyLicenseCaseService countyLicenseCaseService;

  @Inject
  private ApplicationAndLicenseStatusHistoryService applicationAndLicenseStatusHistoryService;

  private PlacementHomeUcDao placementHomeUcDao;

  private CountyOwnershipDao countyOwnershipDao;

  private ExternalInterfaceDao externalInterfaceDao;

  private EmergencyContactDetailDao emergencyContactDetailDao;

  private PlacementHomeProfileDao placementHomeProfileDao;

  private PlacementFacilityTypeHistoryDao placementFacilityTypeHistoryDao;

  private OtherChildrenInPlacementHomeDao otherChildrenInPlacementHomeDao;

  private OtherAdultsInPlacementHomeDao otherAdultsInPlacementHomeDao;

  private OutOfStateCheckDao outOfStateCheckDao;

  private BackgroundCheckDao backgroundCheckDao;

  private SsaName3Dao ssaName3Dao;

  /**
   * Constructor with injected services.
   */
  @Inject
  public PlacementHomeCoreService(XaDaoProvider xaDaoFacade, NonXaDaoProvider nonXaDaoFacade) {
    super(xaDaoFacade.getDao(PlacementHomeDao.class), nonXaDaoFacade.getDao(PlacementHomeDao.class));
    placementHomeUcDao = xaDaoFacade.getDao(PlacementHomeUcDao.class);
    countyOwnershipDao = xaDaoFacade.getDao(CountyOwnershipDao.class);
    externalInterfaceDao = xaDaoFacade.getDao(ExternalInterfaceDao.class);
    emergencyContactDetailDao = xaDaoFacade.getDao(EmergencyContactDetailDao.class);
    emergencyContactDetailDao = xaDaoFacade.getDao(EmergencyContactDetailDao.class);
    placementHomeProfileDao = xaDaoFacade.getDao(PlacementHomeProfileDao.class);
    placementFacilityTypeHistoryDao = xaDaoFacade.getDao(PlacementFacilityTypeHistoryDao.class);
    otherChildrenInPlacementHomeDao = xaDaoFacade.getDao(OtherChildrenInPlacementHomeDao.class);
    otherAdultsInPlacementHomeDao = xaDaoFacade.getDao(OtherAdultsInPlacementHomeDao.class);
    outOfStateCheckDao = xaDaoFacade.getDao(OutOfStateCheckDao.class);
    backgroundCheckDao = xaDaoFacade.getDao(BackgroundCheckDao.class);
    ssaName3Dao = xaDaoFacade.getSsaName3Dao();
  }

  @Override
  protected DataAccessServiceLifecycle getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  protected DataAccessServiceLifecycle getCreateLifeCycle() {
    return new CreateLifecycle();
  }

  @Override
  protected DataAccessServiceLifecycle getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Authorize(PlacementHomeResultReadAuthorizer.PLACEMENT_HOME_RESULT_READ_OBJECT)
  @Override
  protected PlacementHome find(Serializable primaryKey, boolean isXaTransaction) {
    return getCrudDao(isXaTransaction).findByFacilityId((String) primaryKey);
  }

  @Override
  protected PlacementHome create(
    @Authorize("placementHome:create:entityAwareDTO.entity") PlacementHomeEntityAwareDTO entityAwareDto,
    boolean isXaTransaction)
      throws DataAccessServicesException {
    return super.create(entityAwareDto, isXaTransaction);
  }

  protected class CreateLifecycle extends DefaultDataAccessLifeCycle<PlacementHomeEntityAwareDTO> {

    @Override
    public void beforeDataProcessing(DataAccessBundle bundle) {
      validateParameters((PlacementHomeEntityAwareDTO) bundle.getAwareDto());
    }

    @Override
    public void afterBusinessValidation(DataAccessBundle bundle) {
      final PlacementHome placementHome = (PlacementHome) bundle.getAwareDto().getEntity();
      placementHome.setIdentifier(generateId());
      placementHome.setLastUpdateId(getStaffPersonId());
      placementHome.setLastUpdateTime(LocalDateTime.now());
    }

    @Override
    public void dataProcessing(DataAccessBundle bundle, PerryAccount perryAccount) {
      businessValidationService.runDataProcessing(bundle.getAwareDto(), perryAccount,
          PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    }

    @Override
    public void businessValidation(DataAccessBundle bundle, PerryAccount perryAccount) {
      businessValidationService.runBusinessValidation(bundle.getAwareDto(), perryAccount,
          PlacementHomeDroolsConfiguration.INSTANCE);
    }

    @Override
    public void afterStore(DataAccessBundle bundle) throws DataAccessServicesException {
      final PlacementHomeEntityAwareDTO placementHomeEntityAwareDto =
          (PlacementHomeEntityAwareDTO) bundle.getAwareDto();
      createPlacementHomeUc(placementHomeEntityAwareDto);
      createCountyOwnership(placementHomeEntityAwareDto);
      createExternalInterface();
      createBackgroundCheck();
      createEmergencyContactDetail(placementHomeEntityAwareDto);
      createPlacementHomeProfile(placementHomeEntityAwareDto);
      createPlacementFacilityTypeHistory(placementHomeEntityAwareDto);
      createSubstituteCareProviders(placementHomeEntityAwareDto);
      createOtherAdultsInHome(placementHomeEntityAwareDto);
      createOtherChildrenInHome(placementHomeEntityAwareDto);
      createCountyLicenseCase(placementHomeEntityAwareDto);
      createApplicationAndLicenseStatusHistory(placementHomeEntityAwareDto);
      prepareAddressPhoneticSearchKeywords(placementHomeEntityAwareDto.getEntity());
    }

    private void validateParameters(PlacementHomeEntityAwareDTO placementHomeParameterObject) {
      checkNotPersisted(placementHomeParameterObject.getEntity());
      ParametersValidator
          .validateParameterObjects(placementHomeParameterObject.getScpParameterObjects());
      ParametersValidator.validateParameterObjects(
          placementHomeParameterObject.getOtherAdultInHomeParameterObjects());
      ParametersValidator.validateParameterObjects(
          placementHomeParameterObject.getOtherChildrenInHomeParameterObjects());
    }

    private void createBackgroundCheck() {
      final BackgroundCheck backgroundCheck = new BackgroundCheck();
      backgroundCheck.setIdentifier(IdGenerator.generateId());
      backgroundCheck.setBkgrchkc((short) -1);
      backgroundCheck.setBkgrchkDt(LocalDate.now());
      backgroundCheck.setLstUpdId(getStaffPersonId());
      backgroundCheck.setLstUpdTs(LocalDateTime.now());
      backgroundCheckDao.create(backgroundCheck);
    }

    private void createOtherChildrenInHome(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      for (OtherChildInHomeEntityAwareDTO otherChildInHomeParameterObject : parameterObject
          .getOtherChildrenInHomeParameterObjects()) {
        createOtherChildInHome(placementHome, otherChildInHomeParameterObject);
      }
    }

    private void createOtherChildInHome(PlacementHome placementHome,
        OtherChildInHomeEntityAwareDTO parameterObject) {
      OtherChildrenInPlacementHome otherChildInPlacementHome = parameterObject.getEntity();
      otherChildInPlacementHome.setLstUpdId(getStaffPersonId());
      otherChildInPlacementHome.setLstUpdTs(LocalDateTime.now());
      otherChildInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
      otherChildInPlacementHome.setIdentifier(generateId());
      otherChildrenInPlacementHomeDao.create(otherChildInPlacementHome);
    }

    private void createOtherAdultsInHome(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      for (OtherAdultInHomeEntityAwareDTO adultInHomeParameterObject : parameterObject
          .getOtherAdultInHomeParameterObjects()) {
        createOtherAdultInHome(placementHome, adultInHomeParameterObject);
        createAdultOutOfStateChecks(adultInHomeParameterObject);
      }
    }

    private void createAdultOutOfStateChecks(OtherAdultInHomeEntityAwareDTO parameterObject) {
      final OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
      for (OutOfStateCheck outOfStateCheck : parameterObject.getOutOfStateChecks()) {
        outOfStateCheck.setIdentifier(generateId());
        outOfStateCheck.setRcpntCd("O");
        outOfStateCheck.setRcpntId(otherAdultInPlacementHome.getIdentifier());
        outOfStateCheck.setLstUpdId(getStaffPersonId());
        outOfStateCheck.setLstUpdTs(LocalDateTime.now());
        outOfStateCheckDao.create(outOfStateCheck);
      }
    }

    private void createOtherAdultInHome(PlacementHome placementHome,
        OtherAdultInHomeEntityAwareDTO parameterObject) {
      final OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
      otherAdultInPlacementHome.setLstUpdId(getStaffPersonId());
      otherAdultInPlacementHome.setLstUpdTs(LocalDateTime.now());
      otherAdultInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
      otherAdultInPlacementHome.setIdentifier(generateId());
      otherAdultsInPlacementHomeDao.create(otherAdultInPlacementHome);
    }

    private void createSubstituteCareProviders(PlacementHomeEntityAwareDTO parameterObject)
        throws DataAccessServicesException {
      final PlacementHome placementHome = parameterObject.getEntity();
      for (SCPEntityAwareDTO scpParameterObject : parameterObject.getScpParameterObjects()) {
        scpParameterObject.setPlacementHomeId(placementHome.getIdentifier());
        SubstituteCareProvider substituteCareProvider =
            substituteCareProviderService.create(scpParameterObject);
        if (scpParameterObject.isPrimaryApplicant()) {
          placementHome.setPrimarySubstituteCareProvider(substituteCareProvider);
        }
      }
    }

    private void createCountyOwnership(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      final CountyOwnership countyOwnership = countyOwnershipMapper
          .toCountyOwnership(placementHome.getIdentifier(), "P", Collections.emptyList());
      countyOwnershipDao.create(countyOwnership);
    }

    private void createPlacementHomeUc(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHomeUc placementHomeUc = new PlacementHomeUc();
      final PlacementHome placementHome = parameterObject.getEntity();
      placementHomeUc.setCityNm(StringUtils.upperCase(placementHome.getCityNm()));
      placementHomeUc.setGeoRgntcd(StringUtils.upperCase(placementHome.getGeoRgntcd()));
      placementHomeUc.setLaVndrId(StringUtils.upperCase(placementHome.getLaVndrId()));
      placementHomeUc.setLicenseNo(StringUtils.upperCase(placementHome.getLicenseNo()));
      placementHomeUc.setFacltyNm(StringUtils.upperCase(placementHome.getFacltyNm()));
      placementHomeUc.setStreetNo(StringUtils.upperCase(placementHome.getStreetNo()));
      placementHomeUc.setStreetNm(StringUtils.upperCase(placementHome.getStreetNm()));
      placementHomeUc.setPkplcHmt(placementHome.getIdentifier());
      placementHomeUc.setLstUpdId(getStaffPersonId());
      placementHomeUc.setLstUpdTs(LocalDateTime.now());
      placementHomeUcDao.create(placementHomeUc);
    }

    private void createExternalInterface() {
      final ExternalInterface externalInterface = externalInterfaceMapper.toExternalInterface("");
      externalInterfaceDao.create(externalInterface);
    }

    private void createEmergencyContactDetail(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      final EmergencyContactDetail emergencyContactDetail =
          parameterObject.getEmergencyContactDetail();
      if (emergencyContactDetail != null) {
        emergencyContactDetail.setEstblshCd("P"); // P = PLACEMENT HOME
        emergencyContactDetail.setEstblshId(placementHome.getIdentifier());
        emergencyContactDetail.setLstUpdId(getStaffPersonId());
        emergencyContactDetail.setLstUpdTs(LocalDateTime.now());
        emergencyContactDetail.setIdentifier(generateId());
        emergencyContactDetailDao.create(emergencyContactDetail);
      }
    }

    private void createPlacementHomeProfile(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      for (CWSIdentifier homeLanguage : parameterObject.getHomeLanguages()) {
        final PlacementHomeProfile placementHomeProfile = new PlacementHomeProfile();
        placementHomeProfile.setThirdId(generateId());
        placementHomeProfile.setChrctrC((short) homeLanguage.getCwsId());
        placementHomeProfile.setChrctrCd("L");
        placementHomeProfile.setLstUpdId(getStaffPersonId());
        placementHomeProfile.setLstUpdTs(LocalDateTime.now());
        placementHomeProfile.setFkplcHmT(placementHome.getIdentifier());
        placementHomeProfileDao.create(placementHomeProfile);
      }
    }

    /**
     * Rule: R - 11179
     * <p>
     * If the placement home is being saved to the database for the first time then create a new
     * Placement Facility Type History row.
     * </p>
     * <p>
     * Logic If (in focus) PLACEMENT_HOME is saved to the database for the first time then create
     * PLACEMENT_HOME > PLACEMENT_FACILITY_TYPE_HISTORY set .Start_Timestamp = System Timestamp AND
     * .Placement_Facility_Type = (in focus) PLACEMENT_HOME.Placement_Facility_Type.
     * </p>
     */
    private void createPlacementFacilityTypeHistory(PlacementHomeEntityAwareDTO parameterObject) {
      final PlacementHome placementHome = parameterObject.getEntity();
      PlacementFacilityTypeHistory placementFacilityTypeHistory =
          new PlacementFacilityTypeHistory();
      placementFacilityTypeHistory.setThirdId(generateId());
      placementFacilityTypeHistory.setFkplcHmT(placementHome.getIdentifier());
      placementFacilityTypeHistory.setPlacementFacilityType(placementHome.getFacilityType());
      placementFacilityTypeHistory.setStartTimestamp(LocalDateTime.now());
      placementFacilityTypeHistory.setCreationTimestamp(LocalDateTime.now());
      placementFacilityTypeHistory.setLastUpdateTimestamp(LocalDateTime.now());
      placementFacilityTypeHistory.setLastUpdateId(getStaffPersonId());
      placementFacilityTypeHistoryDao.create(placementFacilityTypeHistory);
    }

    private void createCountyLicenseCase(PlacementHomeEntityAwareDTO parameterObject)
        throws DataAccessServicesException {
      final PlacementHome placementHome = parameterObject.getEntity();
      final CLCEntityAwareDTO clcEntityAwareDTO = parameterObject.getCountyLicenseCase();
      clcEntityAwareDTO.setPlacementHomeId(placementHome.getIdentifier());
      CountyLicenseCase countyLicenseCase = countyLicenseCaseService.xaCreate(clcEntityAwareDTO);
      placementHome.setCountyLicenseCase(countyLicenseCase);
    }

    private void createApplicationAndLicenseStatusHistory(
        PlacementHomeEntityAwareDTO parameterObject) throws DataAccessServicesException {
      for (AppAndLicHistoryAwareDTO appAndLicHistoryAwareDTO : parameterObject
          .getAppAndLicHistory()) {
        applicationAndLicenseStatusHistoryService.xaCreate(appAndLicHistoryAwareDTO);
      }
    }

    private void prepareAddressPhoneticSearchKeywords(PlacementHome placementHome) {
      final SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
      parameterObject.setTableName(PhoneticSearchTables.ADR_PHTT);
      parameterObject.setCrudOper(INSERT_OPERATION_CODE);
      parameterObject.setIdentifier(placementHome.getIdentifier());
      parameterObject.setNameCd(PLACEMENT_HOME_ADDRESS);
      parameterObject.setStreetNumber(placementHome.getStreetNo());
      parameterObject.setStreetName(placementHome.getStreetNm());
      parameterObject.setGvrEntc(placementHome.getGvrEntc());
      parameterObject.setUpdateTimeStamp(new Date());
      parameterObject.setUpdateId(placementHome.getLastUpdateId());
      ssaName3Dao.callStoredProc(parameterObject);
    }
  }

}
