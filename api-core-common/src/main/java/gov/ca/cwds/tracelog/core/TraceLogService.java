package gov.ca.cwds.tracelog.core;

import java.util.Map;

import gov.ca.cwds.tracelog.elastic.CaresSearchQueryParser.CaresJsonField;

/**
 * Persist user trace logs. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface TraceLogService {

  /**
   * Log search query terms.
   * 
   * @param userId user conducting the search
   * @param json search query
   * @return map of term/value pairs
   */
  Map<CaresJsonField, String> logSearchQuery(String userId, String json);

  /**
   * Log access to a records, such as a Client.
   * 
   * @param userId user accessing the record
   * @param entity record object
   * @param id primary key
   */
  void logRecordAccess(String userId, Object entity, String id);

}
