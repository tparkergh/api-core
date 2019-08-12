package gov.ca.cwds.tracelog;

import java.io.Serializable;

/**
 * Determine which types of records to trace access for, such as Client or Referral. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface CaresHibernateTraceLogFilter {

  boolean trace(String user, Serializable id, String action);

}
