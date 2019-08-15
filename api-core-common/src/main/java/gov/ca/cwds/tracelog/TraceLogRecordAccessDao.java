package gov.ca.cwds.tracelog;

import java.time.LocalDateTime;

/**
 * Persist user record access, such as clients viewed.
 * 
 * @author CWDS API Team
 */
public interface TraceLogRecordAccessDao {

  void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType);

}
