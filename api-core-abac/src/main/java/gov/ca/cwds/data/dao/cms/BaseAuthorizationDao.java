package gov.ca.cwds.data.dao.cms;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reliable session acquisition, regardless of datasource or transaction boundaries.
 * 
 * @author CWDS API Team
 */
public class BaseAuthorizationDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseAuthorizationDao.class);

  private static final ThreadLocal<Boolean> bound = new ThreadLocal<>();

  protected final SessionFactory sessionFactory;

  /**
   * Default constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  BaseAuthorizationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Get the current session, if any, or open a new one.
   *
   * @return active session, true if opened a new session
   */
  @SuppressWarnings("squid:S2095") // the session is returned from here, so can't be closed here
  Pair<Session, Boolean> grabSession() {
    boolean openedNew = false;
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
      openedNew = true;
      LOGGER.info("Current session is unavailable, opening a new session", e);
    }
    if (isManaged()) {
      ManagedSessionContext.bind(session);
    }

    return Pair.of(session, openedNew);
  }

  public static void setXaMode(boolean xaMode) {
    bound.set(xaMode);
  }

  public static void clearXaMode() {
    bound.remove();
  }

  private boolean getXaMode() {
    final Boolean ret = bound.get();
    return ret != null && ret;
  }

  void finalizeSession() {
    if (isManaged()) {
      ManagedSessionContext.unbind(sessionFactory);
    }
  }

  private boolean isManaged() {
    return !getXaMode();
  }

}
