package gov.ca.cwds.tracelog;

import java.util.Date;

/**
 * Persist user record access, such as clients viewed.
 * 
 * @author CWDS API Team
 */
public interface TraceLogRecordAccessDao {

  void logRecordAccess(String userId, Date moment, String id, String entityType);

}
