package gov.ca.cwds.tracelog;

/**
 * Persist user search query terms.
 * 
 * @author CWDS API Team
 */
public interface TraceLogSearchQueryDao {

  void saveSearchQuery(String userId, String term, String value);

}
