package gov.ca.cwds.tracelog.core;

import java.io.Serializable;

/**
 * Determine which types of records to trace access for, such as Client or Referral. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface TraceLogFilter {

  /**
   * Trace user access to persisted records. Especially important for sealed and sensitive records.
   * 
   * @param user user id
   * @param entity record entity
   * @param id record id
   * @return true = trace this action
   */
  boolean traceAccess(String user, Object entity, Serializable id);

}
