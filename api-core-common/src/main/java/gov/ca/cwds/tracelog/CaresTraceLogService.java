package gov.ca.cwds.tracelog;

/**
 * Persist user trace logs. See CMO-99.
 * 
 * @author CWDS API Team
 */
public interface CaresTraceLogService {

  void logSearchQuery(String userId, String term, String value);

  void logRecordAccess(String userId, String id);

}
