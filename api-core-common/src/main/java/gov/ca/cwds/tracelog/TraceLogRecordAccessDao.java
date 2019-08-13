package gov.ca.cwds.tracelog;

/**
 * Persist user record access, such as clients viewed.
 * 
 * @author CWDS API Team
 */
public interface TraceLogRecordAccessDao {

  void logRecordAccess(String userId, Object entity, String id);

}
