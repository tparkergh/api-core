package gov.ca.cwds.tracelog.core;

import java.time.LocalDateTime;
import java.util.Queue;

import gov.ca.cwds.tracelog.async.TraceLogSearchEntry;

/**
 * Persist user search query terms.
 * 
 * @author CWDS API Team
 */
public interface TraceLogSearchQueryDao {

  /**
   * Persist a single search query entry.
   * 
   * @param userId user's login
   * @param moment moment of search
   * @param term search term
   * @param value search term value
   */
  void logSearchQuery(String userId, LocalDateTime moment, String term, String value);

  /**
   * Log user search query in bulk.
   * 
   * @param searchQueue Queue to drain
   */
  void logBulkAccess(Queue<TraceLogSearchEntry> searchQueue);

}
