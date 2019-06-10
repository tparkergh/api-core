package gov.ca.cwds.rest.services.cms;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import io.dropwizard.jackson.Jackson;

@SuppressWarnings({"javadoc", "squid:S2925"})
public class CachingSystemCodeServiceTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
  private static SystemMetaListResponse systemMetaListResponse;
  private static SystemCodeListResponse systemCodeListResponse;
  private static CachingSystemCodeService cachingSystemCodeService;
  private static CachingSystemCodeService cachingSystemCodeServicePreLoaded;

  @BeforeClass
  public static void setupClass() throws Exception {
    SystemCodeDao systemCodeDao = mock(SystemCodeDao.class);
    SystemMetaDao systemMetaDao = mock(SystemMetaDao.class);

    systemMetaListResponse = MAPPER.readValue(
        fixture("fixtures/rest/services/cms/CachingSystemCodeService/test-syetm-codes-meta.json"),
        SystemMetaListResponse.class);
    gov.ca.cwds.data.persistence.cms.SystemMeta[] persistenceSystemMetas =
        createPersistenceSystemMetas(systemMetaListResponse);

    systemCodeListResponse = MAPPER.readValue(
        fixture("fixtures/rest/services/cms/CachingSystemCodeService/test-system-codes.json"),
        SystemCodeListResponse.class);
    gov.ca.cwds.data.persistence.cms.SystemCode[] persistenceSystemCodes =
        createPersistenceSystemCodes(systemCodeListResponse);

    when(systemMetaDao.findAll()).thenReturn(persistenceSystemMetas);
    when(systemCodeDao.findByForeignKeyMetaTable("ADDR_TPC")).thenReturn(persistenceSystemCodes);
    when(systemCodeDao.findBySystemCodeId((short) 27))
        .thenReturn(getPersistenceSystemCode(systemCodeListResponse, (short) 27));
    when(systemCodeDao.findByForeignKeyMetaTable("GVR_ENTC")).thenReturn(persistenceSystemCodes);

    cachingSystemCodeService = new CachingSystemCodeService(systemCodeDao, systemMetaDao, 1, false);
    cachingSystemCodeServicePreLoaded =
        new CachingSystemCodeService(systemCodeDao, systemMetaDao, 1, true);
  }

  @Test
  public void testAllSystemMetas() throws Exception {
    SystemMetaListResponse actualSystemMetaListResponse =
        (SystemMetaListResponse) cachingSystemCodeService.find(null);
    Assert.assertNotNull(actualSystemMetaListResponse);
    Assert.assertEquals(systemMetaListResponse, actualSystemMetaListResponse);

    Set<SystemMeta> actualMetas = cachingSystemCodeService.getAllSystemMetas();
    Assert.assertNotNull(actualMetas);
    Assert.assertEquals(systemMetaListResponse.getSystemMetas(), actualMetas);
  }

  @Test
  public void testAllSystemCodes() throws Exception {
    Set<SystemCode> actualCodes = cachingSystemCodeService.getAllSystemCodes();
    Assert.assertNotNull(actualCodes);
    Assert.assertEquals(systemCodeListResponse.getSystemCodes(), actualCodes);
  }

  @Test
  public void testSystemCodesForMeta() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("ADDR_TPC");
    Assert.assertNotNull(actualSystemCodeListResponse);
    Assert.assertEquals(systemCodeListResponse, actualSystemCodeListResponse);

    Set<SystemCode> actualCodes = cachingSystemCodeService.getSystemCodesForMeta("ADDR_TPC");
    Assert.assertNotNull(actualCodes);
    Assert.assertEquals(systemCodeListResponse.getSystemCodes(), actualCodes);
  }

  @Test
  public void testSystemCodeDescription() throws Exception {
    String shortDescription = cachingSystemCodeService.getSystemCodeShortDescription((short) 27);
    Assert.assertNotNull(shortDescription);
    Assert.assertEquals("Business", shortDescription);

    shortDescription = cachingSystemCodeService.getSystemCodeShortDescription(null);
    Assert.assertNull(shortDescription);

    shortDescription = cachingSystemCodeService.getSystemCodeShortDescription((short) 999999);
    Assert.assertNull(shortDescription);
  }

  @Test
  public void testVerifySystemCodeDescription() throws Exception {
    boolean validCode =
        cachingSystemCodeService.verifyActiveSystemCodeDescriptionForMeta("Business", "ADDR_TPC");
    Assert.assertTrue(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeDescriptionForMeta("Business-kjhkh",
        "ADDR_TPC");
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeDescriptionForMeta("Business", " ");
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeDescriptionForMeta("Business", null);
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeDescriptionForMeta(null, null);
    Assert.assertFalse(validCode);
  }


  @Test
  public void testGetSystemCodeId() throws Exception {
    Assert.assertEquals(new Short("27"),
        cachingSystemCodeService.getSystemCodeId("Business", "ADDR_TPC"));
  }

  @Test
  public void testGetSystemCodeIdIsNotExist() throws Exception {
    Assert.assertNull(cachingSystemCodeService.getSystemCodeId("Other description", "ADDR_TPC"));
  }

  @Test
  public void testVerifySystemCodeId() throws Exception {
    boolean validCode =
        cachingSystemCodeService.verifyActiveSystemCodeIdForMeta((short) 27, "ADDR_TPC", false);
    Assert.assertTrue(validCode);

    validCode =
        cachingSystemCodeService.verifyActiveSystemCodeIdForMeta((short) 99999, "ADDR_TPC", false);
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeIdForMeta((short) 99999, " ", false);
    Assert.assertFalse(validCode);

    validCode =
        cachingSystemCodeService.verifyActiveSystemCodeIdForMeta((short) 99999, null, false);
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveSystemCodeIdForMeta(null, null, false);
    Assert.assertFalse(validCode);
  }

  @Test
  public void testVerifyLogicalId() throws Exception {
    boolean validCode = cachingSystemCodeService.verifyActiveLogicalIdForMeta("01", "GVR_ENTC");
    Assert.assertTrue(validCode);

    validCode = cachingSystemCodeService.verifyActiveLogicalIdForMeta("99999", "GVR_ENTC");
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveLogicalIdForMeta("99999", " ");
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveLogicalIdForMeta("99999", null);
    Assert.assertFalse(validCode);

    validCode = cachingSystemCodeService.verifyActiveLogicalIdForMeta(null, null);
    Assert.assertFalse(validCode);
  }

  // @Test
  // public void testGlobalRegistration() throws Exception {
  // SystemCodeCache global = SystemCodeCache.global();
  // Assert.assertEquals(cachingSystemCodeService, global);
  // }

  @Test
  public void testNotExtistingSystemMeta() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("hjgjgjgjgjhgj");
    Assert.assertNull(actualSystemCodeListResponse);
  }

  @Test
  public void testSystemCode() throws Exception {
    SystemCode expectedSystemCode = new SystemCode((short) 27, (short) 0, "N", "", "Business",
        "0001", "0000000000", "ADDR_TPC", "");
    SystemCode actualSystemCode = cachingSystemCodeService.getSystemCode((short) 27);
    Assert.assertNotNull(actualSystemCode);
    Assert.assertEquals(expectedSystemCode, actualSystemCode);
  }

  @Test
  public void testCacheReLoading() throws Exception {
    SystemCodeListResponse actualSystemCodeListResponse =
        (SystemCodeListResponse) cachingSystemCodeService.find("ADDR_TPC");
    Assert.assertNotNull(actualSystemCodeListResponse);
    Assert.assertEquals(systemCodeListResponse, actualSystemCodeListResponse);

    /**
     * Wait to invalidate cache.
     */
    Thread.currentThread().sleep(2000);

    SystemCode expectedSystemCode = new SystemCode((short) 27, (short) 0, "N", "", "Business",
        "0001", "0000000000", "ADDR_TPC", "");
    SystemCode actualSystemCode = cachingSystemCodeService.getSystemCode((short) 27);
    Assert.assertNotNull(actualSystemCode);
    Assert.assertEquals(expectedSystemCode, actualSystemCode);

    /**
     * Wait to invalidate cache.
     */
    Thread.currentThread().sleep(2000);

    Set<SystemCode> systemCodesForMeta = cachingSystemCodeService.getSystemCodesForMeta("ADDR_TPC");
    Assert.assertNotNull(systemCodesForMeta);
    Assert.assertEquals(12, systemCodesForMeta.size());

  }

  private static gov.ca.cwds.data.persistence.cms.SystemMeta[] createPersistenceSystemMetas(
      SystemMetaListResponse systemMetaListResponse) {
    Set<SystemMeta> systemMetas = systemMetaListResponse.getSystemMetas();
    Set<gov.ca.cwds.data.persistence.cms.SystemMeta> persistenceSystemMetas = new HashSet<>();

    for (SystemMeta syetmMeta : systemMetas) {
      persistenceSystemMetas.add(createPersistenceSystemMeta(syetmMeta));
    }

    return persistenceSystemMetas.toArray(new gov.ca.cwds.data.persistence.cms.SystemMeta[0]);
  }

  private static gov.ca.cwds.data.persistence.cms.SystemMeta createPersistenceSystemMeta(SystemMeta systemMeta) {
    return new gov.ca.cwds.data.persistence.cms.SystemMeta(systemMeta.getLogicalTableDsdName(), systemMeta.getUserTableName(),
      systemMeta.getShortDescriptionName());
  }

  private static gov.ca.cwds.data.persistence.cms.SystemCode[] createPersistenceSystemCodes(
      SystemCodeListResponse systemCodeListResponse) {

    Set<SystemCode> systemCodes = systemCodeListResponse.getSystemCodes();
    Set<gov.ca.cwds.data.persistence.cms.SystemCode> persistenceSystemCodes = new HashSet<>();

    for (SystemCode systemCode : systemCodes) {
      persistenceSystemCodes.add(createPersistenceSystemCode(systemCode));
    }

    return persistenceSystemCodes.toArray(new gov.ca.cwds.data.persistence.cms.SystemCode[0]);
  }

  private static gov.ca.cwds.data.persistence.cms.SystemCode getPersistenceSystemCode(
      SystemCodeListResponse systemCodeListResponse, Short systemCodeId) {

    Set<SystemCode> systemCodes = systemCodeListResponse.getSystemCodes();
    gov.ca.cwds.data.persistence.cms.SystemCode persistenceSystemCode = null;

    for (SystemCode systemCode : systemCodes) {
      if (systemCodeId.equals(systemCode.getSystemId())) {
        persistenceSystemCode = createPersistenceSystemCode(systemCode);
        break;
      }
    }

    return persistenceSystemCode;
  }

  private static gov.ca.cwds.data.persistence.cms.SystemCode createPersistenceSystemCode(SystemCode systemCode) {
    return new gov.ca.cwds.data.persistence.cms.SystemCode(systemCode.getSystemId(), systemCode.getCategoryId(), systemCode.getInactiveIndicator(),
      systemCode.getOtherCd(), systemCode.getShortDescription(), systemCode.getLogicalId(), systemCode.getThirdId(), systemCode.getForeignKeyMetaTable(), systemCode.getLongDescription());
  }
}
