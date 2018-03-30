package gov.ca.cwds.data.legacy.cms;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Base class for objects in the legacy, DB2 persistence layer.
 *
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class CmsPersistentObject extends CmsPersistentObjectBase {

  /**
   * LAST_UPDATE_TIMESTAMP - The date and time of the most recent update of an occurrence of this
   * entity type.
   */
  @Column(name = "LST_UPD_TS", nullable = false)
  private LocalDateTime lastUpdatedTime;

  /**
   * @return the time and date of the most recent update to an occurrence of this entity type.
   */
  public LocalDateTime getLastUpdateTime() {
    return lastUpdatedTime;
  }

  @SuppressWarnings("javadoc")
  public void setLastUpdateTime(LocalDateTime lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }
}
