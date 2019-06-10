package gov.ca.cwds.rest.api.domain.cms;

import java.util.Set;

import gov.ca.cwds.data.persistence.cms.DeferredRegistry;
import gov.ca.cwds.data.std.ApiMarker;

/**
 * Interface for the CMS system code cache facility.
 * 
 * @author CWDS API Team
 * @see DeferredRegistry
 */
public interface SystemCodeCache extends ApiMarker {

  /**
   * Register this system code cache instance for system-wide use.
   */
  default void register() {
    DeferredRegistry.<SystemCodeCache>register(SystemCodeCache.class, this);
  }

  /**
   * Globally available, singleton system code cache.
   * 
   * @return singleton system code cache
   */
  static SystemCodeCache global() {
    return DeferredRegistry.<SystemCodeCache>unwrap(SystemCodeCache.class);
  }

  /**
   * Get all system meta (system code category) definitions.
   * 
   * @return All system meta definitions.
   */
  Set<SystemMeta> getAllSystemMetas();

  /**
   * Get all system codes for all meta definitions.
   * 
   * @return All system codes for all meta definitions.
   */
  Set<SystemCode> getAllSystemCodes();

  /**
   * Get system code identified by given system code identifier.
   * 
   * @param systemCodeId System code identifier.
   * @return System code if found, otherwise null.
   */
  SystemCode getSystemCode(final Number systemCodeId);

  /**
   * Get system codes for given meta identifier.
   * 
   * @param metaId Meta identifier.
   * @return System codes for given meta identifier.
   */
  Set<SystemCode> getSystemCodesForMeta(final String metaId);

  /**
   * Get short description of system code identified by given system code id.
   * 
   * @param systemCodeId System code ID
   * @return Short description of system code identified by given system code id. Returns null if
   *         system code not found.
   */
  String getSystemCodeShortDescription(final Number systemCodeId);

  /**
   * Get system code id of system code identified by given meta id and short description.
   *
   * @param metaId meta (category) ID
   * @param shortDescription syscode short description
   * @return Short description of system code identified by given system code id. Returns null if
   *         system code not found.
   */
  Short getSystemCodeId(String shortDescription, String metaId);

  /**
   * Get system code descriptor for given system code id.
   * 
   * @param systemCodeId System code ID
   * @return System code descriptor for given system code id.
   */
  SystemCodeDescriptor getSystemCodeDescriptor(final Number systemCodeId);

  /**
   * Verify that given active system code ID exists for given meta ID. For some fields the
   * corresponding category id must be zero for it to be a valid system code for the field
   * 
   * @param systemCodeId System code ID
   * @param metaId Meta (category) ID
   * @param checkCategoryIdValueIsZero check that category id is 0
   * @return True if active system code ID exists under given meta.
   */
  boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId,
      boolean checkCategoryIdValueIsZero);

  /**
   * Verify that given active system code ID exists for given meta ID.
   *
   * @param logicalId Logical ID
   * @param metaId Meta (category) ID
   * @return True if active Logical ID exists under given meta.
   */
  boolean verifyActiveLogicalIdForMeta(String logicalId, String metaId);

  /**
   * Verify that given active system code short description exists for given meta. ID.
   * 
   * @param shortDesc System code short description
   * @param metaId Meta (category) ID
   * @return True if active system code with given description exists under given meta.
   */
  boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId);

  /**
   * Get number of items in cache.
   * 
   * @return Number of items in cache. If returns -1 then, size is unknown.
   */
  default long getCacheSize() {
    return -1;
  }

}
