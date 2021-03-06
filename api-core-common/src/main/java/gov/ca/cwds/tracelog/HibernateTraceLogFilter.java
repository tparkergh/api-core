package gov.ca.cwds.tracelog;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import gov.ca.cwds.tracelog.core.TraceLogFilter;

/**
 * Log access to record types under watch and ignore other entity classes. If no watch classes are
 * set, then trace access for all entity classes.
 * 
 * @author CWDS API Team
 */
public class HibernateTraceLogFilter implements TraceLogFilter {

  private final Set<Class<?>> watchClasses;

  public HibernateTraceLogFilter() {
    watchClasses = new HashSet<>();
  }

  public HibernateTraceLogFilter(Collection<Class<?>> klasses) {
    watchClasses = new HashSet<>(klasses);
  }

  @Override
  public boolean traceAccess(String user, Object entity, Serializable id) {
    boolean ret = watchClasses.isEmpty();

    if (!ret && entity != null) {
      ret = watchClasses.stream().anyMatch(c -> c.isInstance(entity));
    }

    return ret;
  }

}
