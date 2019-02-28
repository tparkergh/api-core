package gov.ca.cwds.rest.services.cms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeDescriptor;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.validation.LogicalIdLovValidation;
import gov.ca.cwds.rest.validation.SystemCodeIdLovLValidation;

/**
 * System code caching implementation.
 * 
 * @author CWDS API Team
 */
@SuppressFBWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
public class CachingSystemCodeService extends SystemCodeService implements SystemCodeCache {

  private static final long serialVersionUID = 1468150983558929580L;

  private static final Logger LOGGER = LoggerFactory.getLogger(CachingSystemCodeService.class);

  /**
   * System codes cache.
   */
  private transient LoadingCache<CacheKey, Object> systemCodeCache;

  /**
   * Default no-arg constructor.
   */
  @SuppressWarnings("unused")
  private CachingSystemCodeService() {
    // No-op.
  }

  /**
   * Construct the object.
   * 
   * @param systemCodeDao System codes DAO
   * @param systemMetaDao System meta DAO
   * @param secondsToRefreshCache Seconds after which cache entries will be invalidated for refresh.
   * @param preloadCache If true then preload all system code cache
   */
  @Inject
  public CachingSystemCodeService(SystemCodeDao systemCodeDao, SystemMetaDao systemMetaDao,
      long secondsToRefreshCache, boolean preloadCache) {
    super(systemCodeDao, systemMetaDao);

    final SystemCodeCacheLoader cacheLoader = new SystemCodeCacheLoader(this);
    systemCodeCache = CacheBuilder.newBuilder()
        .refreshAfterWrite(secondsToRefreshCache, TimeUnit.SECONDS).build(cacheLoader);

    if (preloadCache) {
      try {
        Map<CacheKey, Object> systemCodes = cacheLoader.loadAll();
        systemCodeCache.putAll(systemCodes);
      } catch (Exception e) {
        LOGGER.error("Error loading system codes", e);
        throw new ServiceException(e);
      }

      LOGGER.info("System code cache size: {}, pre-loaded: {}, refresh after (seconds): {}",
          systemCodeCache.size(), preloadCache, secondsToRefreshCache);
    }
  }

  @Override
  public Response find(Serializable key) {
    Response response;

    if (key == null || StringUtils.isBlank(key.toString())) {
      response = (Response) getFromCache(CacheKey.createForAllMetas());
    } else {
      response = (Response) getFromCache(CacheKey.createForMeta(key.toString()));
    }

    return response;
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    Set<SystemMeta> systemMetas = new HashSet<>();
    SystemMetaListResponse systemMetaListResponse =
        (SystemMetaListResponse) getFromCache(CacheKey.createForAllMetas());
    if (systemMetaListResponse != null) {
      systemMetas = systemMetaListResponse.getSystemMetas();
    }

    return systemMetas;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    Set<SystemCode> systemCodes = new HashSet<>();
    Set<SystemMeta> systemMetas = getAllSystemMetas();

    if (systemMetas != null) {
      for (SystemMeta systemMeta : systemMetas) {
        systemCodes.addAll(getSystemCodesForMeta(systemMeta.getLogicalTableDsdName()));
      }
    }

    return systemCodes;
  }

  @Override
  public SystemCode getSystemCode(final Number systemCodeId) {
    if (systemCodeId == null || Integer.valueOf("0").equals(systemCodeId.intValue())) {
      return null;
    }

    return (SystemCode) getFromCache(CacheKey.createForSystemCode(systemCodeId));
  }

  @Override
  public String getSystemCodeShortDescription(final Number systemCodeId) {
    String shortDescription = null;
    final SystemCode systemCode = getSystemCode(systemCodeId);
    if (systemCode != null) {
      shortDescription = systemCode.getShortDescription();
    }

    return shortDescription;
  }

  @Override
  public Short getSystemCodeId(String shortDescription, String metaId) {
    Short sysId = null;
    Set<SystemCode> systemCodes = getSystemCodesForMeta(metaId);
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        if (StringUtils.equalsIgnoreCase(StringUtils.trim(shortDescription),
            StringUtils.trim(systemCode.getShortDescription()))) {
          sysId = systemCode.getSystemId();
        }
      }
    }
    return sysId;
  }

  @Override
  public SystemCodeDescriptor getSystemCodeDescriptor(Number systemCodeId) {
    SystemCodeDescriptor systemCodeDescriptor = null;
    SystemCode systemCode = getSystemCode(systemCodeId);
    if (systemCode != null) {
      systemCodeDescriptor = systemCode.getSystemCodeDescriptor();
    }
    return systemCodeDescriptor;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(final String metaId) {
    Set<SystemCode> systemCodes = new HashSet<>();

    if (!StringUtils.isBlank(metaId)) {
      CacheKey cacheKey = CacheKey.createForMeta(metaId);
      SystemCodeListResponse systemCodeListResponse =
          (SystemCodeListResponse) getFromCache(cacheKey);
      if (systemCodeListResponse != null) {
        systemCodes = systemCodeListResponse.getSystemCodes();
      }
    }

    return systemCodes;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId,
      boolean checkCategoryIdValueIsZero) {
    return new SystemCodeIdLovLValidation(getSystemCodesForMeta(metaId)).isValid(systemCodeId,
        checkCategoryIdValueIsZero);
  }

  @Override
  public boolean verifyActiveLogicalIdForMeta(String logicalId, String metaId) {
    return new LogicalIdLovValidation(getSystemCodesForMeta(metaId)).isValid(logicalId);
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    boolean valid = false;
    Set<SystemCode> systemCodes = getSystemCodesForMeta(metaId);
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        if (StringUtils.equalsIgnoreCase(StringUtils.trim(shortDesc),
            StringUtils.trim(systemCode.getShortDescription()))) {
          valid = "N".equalsIgnoreCase(systemCode.getInactiveIndicator());
        }
      }
    }

    return valid;
  }

  @Override
  public long getCacheSize() {
    return systemCodeCache.size();
  }

  /**
   * Get cached object identified by given cache key.
   * 
   * @param cacheEntryKey Cache key
   * @return Cached object if found, otherwise null.
   */
  private Object getFromCache(CacheKey cacheEntryKey) {
    Object obj = null;
    try {
      obj = systemCodeCache.get(cacheEntryKey);
    } catch (Exception e) {
      LOGGER.warn("getFromCache -> Unable to load object for key: " + cacheEntryKey, e);
    }

    return obj;
  }

  /**
   * =============================================================================== <br>
   * Cache loader for system metas and system codes. <br>
   * ===============================================================================
   */
  private static class SystemCodeCacheLoader extends CacheLoader<CacheKey, Object> {

    private SystemCodeService systemCodeService;

    /**
     * Construct the object
     * 
     * @param systemCodeService
     */
    SystemCodeCacheLoader(SystemCodeService systemCodeService) {
      this.systemCodeService = systemCodeService;
    }

    @Override
    public Object load(CacheKey key) throws Exception {
      Object objectToCache = null;

      if (CacheKey.ALL_METAS_TYPE.equals(key.getType())) {
        // Load all system metas
        SystemMetaListResponse metaList =
            (SystemMetaListResponse) systemCodeService.loadSystemMetas();
        objectToCache = metaList;

      } else if (CacheKey.META_ID_TYPE.equals(key.getType())) {
        // Add SystemCodeListResponse objects keyed by Meta ID.
        SystemCodeListResponse sysCodeList =
            (SystemCodeListResponse) systemCodeService.loadSystemCodesForMeta(key.getValue());
        objectToCache = sysCodeList;

      } else if (CacheKey.SYSTEM_CODE_ID_TYPE.equals(key.getType())) {
        // Add SystemCode objects keyed by System Code ID.
        SystemCode systemCode = (SystemCode) systemCodeService.loadSystemCode(key.getValue());
        objectToCache = systemCode;
      }

      return objectToCache;
    }

    /**
     * Loads all system code cache entries.
     * 
     * @return All system code cache entries.
     * @throws Exception on disconnect, NPE, etc.
     */
    public Map<CacheKey, Object> loadAll() throws Exception {
      LOGGER.info("Loading all system code cache...");
      Map<CacheKey, Object> systemCodeMap = new HashMap<>();

      /**
       * Load all system metas and add SystemMetaListResponse object to cache.
       */
      CacheKey cacheKey = CacheKey.createForAllMetas();
      SystemMetaListResponse metaList = (SystemMetaListResponse) load(cacheKey);
      Set<SystemMeta> systemMetaSet = new HashSet<>();
      if (metaList != null) {
        systemCodeMap.put(cacheKey, metaList);
        systemMetaSet = metaList.getSystemMetas();
      }

      final Set<SystemCode> sysCodes = new HashSet<>();
      for (SystemMeta sysMeta : systemMetaSet) {
        sysCodes.clear();
        String metaId = sysMeta.getLogicalTableDsdName();

        /**
         * Add SystemCodeListResponse objects keyed by Meta ID.
         */
        cacheKey = CacheKey.createForMeta(metaId);
        SystemCodeListResponse sysCodeList = (SystemCodeListResponse) load(cacheKey);
        if (sysCodeList != null) {
          systemCodeMap.put(cacheKey, sysCodeList);
          sysCodes.addAll(sysCodeList.getSystemCodes());
        }

        /**
         * Add SystemCode objects keyed by System Code ID.
         */
        for (SystemCode sysCode : sysCodes) {
          cacheKey = CacheKey.createForSystemCode(sysCode.getSystemId());
          systemCodeMap.put(cacheKey, sysCode);
        }
      }

      LOGGER.info("Loaded ALL system code cache. Size: {}", systemCodeMap.size());
      return systemCodeMap;
    }
  }

  /**
   * =============================================================================== <br>
   * Cache key. <br>
   * ===============================================================================
   */
  private static class CacheKey extends ApiObjectIdentity {

    private static final long serialVersionUID = 1L;

    private static final String ALL_METAS_TYPE = "ALL_METAS";
    private static final String META_ID_TYPE = "META_ID";
    private static final String SYSTEM_CODE_ID_TYPE = "SYSTEM_CODE_ID";

    private Serializable value;
    private String type;

    private CacheKey(String type, Serializable value) {
      this.type = type;
      this.value = value;
    }

    public Serializable getValue() {
      return value;
    }

    public String getType() {
      return type;
    }

    private static CacheKey createForAllMetas() {
      return new CacheKey(ALL_METAS_TYPE, ALL_METAS_TYPE);
    }

    private static CacheKey createForMeta(Serializable value) {
      return new CacheKey(META_ID_TYPE, value);
    }

    private static CacheKey createForSystemCode(Serializable value) {
      return new CacheKey(SYSTEM_CODE_ID_TYPE, value);
    }

  }
}
