package gov.ca.cwds.tracelog;

import java.util.Date;

/**
 * Persist user search query terms.
 * 
 * @author CWDS API Team
 */
public interface TraceLogSearchQueryDao {

  void logSearchQuery(String userId, Date moment, String term, String value);

}
