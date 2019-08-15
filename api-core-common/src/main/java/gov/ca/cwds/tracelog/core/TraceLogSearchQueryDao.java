package gov.ca.cwds.tracelog.core;

import java.time.LocalDateTime;

/**
 * Persist user search query terms.
 * 
 * @author CWDS API Team
 */
public interface TraceLogSearchQueryDao {

  void logSearchQuery(String userId, LocalDateTime moment, String term, String value);

}
