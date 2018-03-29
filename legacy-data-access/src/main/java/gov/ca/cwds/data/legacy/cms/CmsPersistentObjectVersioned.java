package gov.ca.cwds.data.legacy.cms;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base class for objects in the legacy, DB2 persistence layer.
 *
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class CmsPersistentObjectVersioned extends ApiObjectIdentity implements PersistentObject {

  /**
   * Baseline serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * All legacy "identifier" fields and their foreign key are CHAR(10).
   */
  public static final int CMS_ID_LEN = 10;

  /**
   * LAST_UPDATE_ID - The ID (a sequential 3 digit base 62 number generated by the system) of the
   * STAFF PERSON or batch program which made the last update to an occurrence of this entity type.
   */
  @Basic
  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
  private String lastUpdatedId;

  /**
   * LAST_UPDATE_TIMESTAMP - The date and time of the most recent update of an occurrence of this
   * entity type.
   */
  @Version
  @Column(name = "LST_UPD_TS", nullable = false)
  private Timestamp lastUpdatedTime;

  /**
   * {@inheritDoc}
   *
   * @see PersistentObject#getPrimaryKey()
   */
  @Override
  public abstract Serializable getPrimaryKey();

  /**
   * @return the ID (a sequential 3 digit base 62 number generated by the system) of the STAFF
   * PERSON or batch program which made the last update to an occurrence of this entity type.
   */
  public String getLastUpdateId() {
    return lastUpdatedId;
  }

  /**
   * @return the time and date of the most recent update to an occurrence of this entity type.
   */
  public Timestamp getLastUpdateTime() {
    return lastUpdatedTime;
  }

  @SuppressWarnings("javadoc")
  public void setLastUpdateId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }

  @SuppressWarnings("javadoc")
  public void setLastUpdateTime(Timestamp lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }
}
