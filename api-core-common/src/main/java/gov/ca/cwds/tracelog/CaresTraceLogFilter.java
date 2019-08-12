package gov.ca.cwds.tracelog;

import java.io.Serializable;

/**
 * Determine which types of records to trace access for, such as Client or Referral. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface CaresTraceLogFilter {

  /**
   * Trace user access to persisted records. Especially important for sealed and sensitive records.
   * 
   * @param user user id
   * @param id record id
   * @param action user action
   * @return true = trace this action
   */
  boolean traceAccess(String user, Serializable id, CaresTraceLogAction action);

}
