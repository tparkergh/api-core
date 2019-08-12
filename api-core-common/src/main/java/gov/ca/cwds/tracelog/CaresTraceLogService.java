package gov.ca.cwds.tracelog;

/**
 * Persist user trace logs. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface CaresTraceLogService {

  /**
   * Log search query terms.
   * 
   * @param userId user conducting the search
   * @param term search term
   * @param value term value
   */
  void logSearchQuery(String userId, String term, String value);

  /**
   * Log access to a records, such as a Client.
   * 
   * @param userId user accessing the record
   * @param id primary key
   * @param type record type
   */
  void logRecordAccess(String userId, String id, String type);

}
