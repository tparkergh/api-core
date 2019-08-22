package gov.ca.cwds.tracelog.core;

import java.time.LocalDateTime;
import java.util.Queue;

import gov.ca.cwds.tracelog.async.TraceLogAccessEntry;

/**
 * Persist user record access, such as clients viewed.
 * 
 * @author CWDS API Team
 */
public interface TraceLogRecordAccessDao {

  /**
   * Persist a single user access action.
   * 
   * @param userId user's login id
   * @param moment moment of access
   * @param id record id
   * @param entityType record type
   */
  void logRecordAccess(String userId, LocalDateTime moment, String id, String entityType);

  /**
   * Log user record access in bulk.
   * 
   * @param accessQueue Queue to drain
   */
  void logBulkAccess(Queue<TraceLogAccessEntry> accessQueue);

}
