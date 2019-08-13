package gov.ca.cwds.tracelog;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Log access to record types under watch.
 * 
 * @author CWDS API Team
 */
public class HibernateTraceLogFilter implements TraceLogFilter {

  private final Set<Class<?>> watchClasses;

  public HibernateTraceLogFilter(Collection<Class<?>> klasses) {
    watchClasses = new HashSet<>(klasses);
  }

  @Override
  public boolean traceAccess(String user, Object entity, Serializable id) {
    boolean ret = false;

    if (entity != null) {
      ret = watchClasses.stream().anyMatch(c -> c.isInstance(entity));
    }

    return ret;
  }

}
