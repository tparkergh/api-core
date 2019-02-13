package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.dao.XaDaoProvider;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderCoreService;
import gov.ca.cwds.data.legacy.cms.dao.CountyOwnershipDao;
import gov.ca.cwds.data.legacy.cms.dao.PlacementHomeInformationDao;
import gov.ca.cwds.data.legacy.cms.dao.ScpOtherEthnicityDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.dao.SubstituteCareProviderDao;
import gov.ca.cwds.data.legacy.cms.dao.SubstituteCareProviderUCDao;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by TPT2 on 12/11/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IdGenerator.class, PrincipalUtils.class})
public class R07045Test {

  private SubstituteCareProviderCoreService scpService;
  private ScpOtherEthnicityDao mockedEthnicityDao;

  @Mock private XaDaoProvider xaDaoProvider;
  @Mock private SubstituteCareProviderDao substituteCareProviderDao;

  @Before
  public void setup() {
    when(xaDaoProvider.getDao(SubstituteCareProviderDao.class)).thenReturn(substituteCareProviderDao);
    scpService = new SubstituteCareProviderCoreService(xaDaoProvider);
    mockedEthnicityDao = mock(ScpOtherEthnicityDao.class);
    scpService.setScpOtherEthnicityDao(mockedEthnicityDao);
    scpService.setBusinessValidationService(mock(BusinessValidationService.class));
    scpService.setSubstituteCareProviderUcDao(mock(SubstituteCareProviderUCDao.class));
    scpService.setPlacementHomeInformationDao(mock(PlacementHomeInformationDao.class));
    scpService.setCountyOwnershipMapper(Mappers.getMapper(CountyOwnershipMapper.class));
    scpService.setCountyOwnershipDao(mock(CountyOwnershipDao.class));
    scpService.setScpSsaName3Dao(mock(SsaName3Dao.class));
  }

  @Test
  public void testRule() throws Exception {
    PowerMockito.mockStatic(IdGenerator.class);
    when(IdGenerator.generateId()).thenReturn("1q2w3e4r");
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn("0X5");
    SCPEntityAwareDTO scpEntityAwareDTO = new SCPEntityAwareDTO();
    scpEntityAwareDTO.setEntity(new SubstituteCareProvider());

    //2 ethnicity was added
    scpEntityAwareDTO.setEthnicityList(Arrays.asList(mock(CWSIdentifier.class), mock(CWSIdentifier.class)));

    SubstituteCareProvider substituteCareProvider = scpService.create(scpEntityAwareDTO);

    //expecting 2 ethnicity to
    verify(mockedEthnicityDao, times(2)).create(any());
  }

}
