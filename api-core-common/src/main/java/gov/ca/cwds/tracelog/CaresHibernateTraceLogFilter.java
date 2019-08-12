package gov.ca.cwds.tracelog;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CaresHibernateTraceLogFilter implements CaresTraceLogFilter {

  private final Set<Class<?>> watchClasses;

  public CaresHibernateTraceLogFilter(Collection<Class<?>> klasses) {
    watchClasses = new HashSet<>(klasses);
  }

  @Override
  public boolean traceAccess(String user, Object entity, Serializable id,
      CaresTraceLogAction action) {
    boolean ret = false;

    if (entity != null) {
      final Class<?> klass = entity.getClass();
      ret = watchClasses.stream().anyMatch(c -> klass.isInstance(c));
    }

    return ret;
  }

}
