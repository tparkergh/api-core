package gov.ca.cwds.data;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.EntityType;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reduce connection pressure on test databases by constructing a single data source (connection
 * pool) for all test cases. The "closer thread" will shut down the shared session factory on method
 * close, if no further unit tests require it. When not in test mode, this class merely wraps a
 * SessionFactory.
 * 
 * <p>
 * Can only be instantiated by child classes and this package.
 * </p>
 * 
 * @author CWDS API Team
 * @see AutocloseSessionFactory
 */
@SuppressFBWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
public class SharedSessionFactory implements SessionFactory {

  private static final long serialVersionUID = 1L;

  private static final int DEFAULT_DELAY = 3500;

  private static final Logger LOGGER = LoggerFactory.getLogger(SharedSessionFactory.class);

  private SessionFactory sf;

  private transient ReadWriteLock lock;
  private transient Condition okToClose;

  private final boolean closeOnSignal;
  private final boolean testMode;

  /**
   * Changes become visible to other threads immediately. Only available to child classes and this
   * package.
   */
  volatile boolean held = true;

  /**
   * Optional constructor. Pass true to run in test mode. The shared session factory will close down
   * when testing completes.
   * 
   * @param sf session factory to wrap
   * @param testMode for JUnit tests
   */
  SharedSessionFactory(SessionFactory sf, boolean testMode) {
    this.sf = sf;
    this.closeOnSignal = testMode;
    this.testMode = testMode;
    this.lock = new ReentrantReadWriteLock();
    this.okToClose = lock.writeLock().newCondition();

    if (this.testMode) {
      runCloseThread();
    }
  }

  /**
   * Constructor for normal production use. Only available to child classes and this package.
   * 
   * @param sf session factory to wrap
   */
  SharedSessionFactory(SessionFactory sf) {
    this(sf, true);
  }

  protected void closerLock() {
    try {
      lock.writeLock().lock();
      held = false;
      okToClose.signalAll();
      LOGGER.warn("SHUTTING DOWN SESSION FACTORY!");
      sf.close();
      LOGGER.warn("SHUT DOWN SESSION FACTORY!");
    } finally {
      lock.writeLock().unlock(); // Always unlock, no matter what!
    }
  }

  protected void closerThread() {
    try {
      Thread.sleep(DEFAULT_DELAY); // NOSONAR
      while (true) {
        LOGGER.debug("Await notification ...");
        okToClose.await(); // Possible spurious wake-up. Must still evaluate the situation.
        Thread.sleep(DEFAULT_DELAY); // NOSONAR

        if (!held) {
          closerLock();
          break;
        }
      }

    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    } finally {
      LOGGER.warn("EXIT SESSION FACTORY CLOSER THREAD");
    }
  }

  /**
   * Launch the "closer thread" in test mode. Unnecessary in production mode.
   */
  final protected void runCloseThread() {
    LOGGER.info("START SESSION FACTORY CLOSER THREAD");
    new Thread(this::closerThread).start();
  }

  @Override
  public void close() {
    try {
      if (!closeOnSignal) {
        // Close normally.
        sf.close();
      } else {
        // Thread calls close() for you.
        held = false;
        okToClose.signalAll();
      }
    } finally {
      lock.readLock().unlock(); // Always unlock on close.
    }
  }

  @Override
  public SessionFactoryImplementor getSessionFactory() {
    return sf.getSessionFactory();
  }

  @Override
  public Reference getReference() throws NamingException {
    return sf.getReference();
  }

  @Override
  public EntityManager createEntityManager() {
    return sf.createEntityManager();
  }

  @Override
  public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> entityClass) {
    return sf.findEntityGraphsByType(entityClass);
  }

  @Override
  public SessionFactoryOptions getSessionFactoryOptions() {
    return sf.getSessionFactoryOptions();
  }

  @Override
  public EntityManager createEntityManager(Map map) {
    return sf.createEntityManager(map);
  }

  @Override
  public SessionBuilder withOptions() {
    return sf.withOptions();
  }

  @Override
  public Session openSession() throws HibernateException {
    return sf.openSession();
  }

  @Override
  public EntityType getEntityTypeByName(String entityName) {
    return sf.getEntityTypeByName(entityName);
  }

  @Override
  public EntityManager createEntityManager(SynchronizationType synchronizationType) {
    return sf.createEntityManager(synchronizationType);
  }

  @Override
  public Session getCurrentSession() throws HibernateException {
    return sf.getCurrentSession();
  }

  @Override
  public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
    return sf.createEntityManager(synchronizationType, map);
  }

  @Override
  public StatelessSessionBuilder withStatelessOptions() {
    return sf.withStatelessOptions();
  }

  @Override
  public StatelessSession openStatelessSession() {
    return sf.openStatelessSession();
  }

  @Override
  public StatelessSession openStatelessSession(Connection connection) {
    return sf.openStatelessSession(connection);
  }

  @Override
  public Statistics getStatistics() {
    return sf.getStatistics();
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return sf.getCriteriaBuilder();
  }

  @Override
  public Metamodel getMetamodel() {
    return sf.getMetamodel();
  }

  @Override
  public boolean isClosed() {
    return sf.isClosed();
  }

  @Override
  public boolean isOpen() {
    return sf.isOpen();
  }

  @Override
  public Cache getCache() {
    return sf.getCache();
  }

  @Override
  public Set getDefinedFilterNames() {
    return sf.getDefinedFilterNames();
  }

  @Override
  public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
    return sf.getFilterDefinition(filterName);
  }

  @Override
  public boolean containsFetchProfileDefinition(String name) {
    return sf.containsFetchProfileDefinition(name);
  }

  @Override
  public Map<String, Object> getProperties() {
    return sf.getProperties();
  }

  @Override
  public TypeHelper getTypeHelper() {
    return sf.getTypeHelper();
  }

  @Override
  public ClassMetadata getClassMetadata(Class entityClass) {
    return sf.getClassMetadata(entityClass);
  }

  @Override
  public PersistenceUnitUtil getPersistenceUnitUtil() {
    return sf.getPersistenceUnitUtil();
  }

  @Override
  public ClassMetadata getClassMetadata(String entityName) {
    return sf.getClassMetadata(entityName);
  }

  @Override
  public void addNamedQuery(String name, Query query) {
    sf.addNamedQuery(name, query);
  }

  @Override
  public CollectionMetadata getCollectionMetadata(String roleName) {
    return sf.getCollectionMetadata(roleName);
  }

  @Override
  public Map<String, ClassMetadata> getAllClassMetadata() {
    return sf.getAllClassMetadata();
  }

  @Override
  public <T> T unwrap(Class<T> cls) {
    return sf.unwrap(cls);
  }

  @Override
  public Map getAllCollectionMetadata() {
    return sf.getAllCollectionMetadata();
  }

  @Override
  public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
    sf.addNamedEntityGraph(graphName, entityGraph);
  }

  protected boolean isHeld() {
    return held;
  }

  protected void setHeld(boolean held) {
    this.held = held;
  }

  protected ReadWriteLock getLock() {
    return lock;
  }

  protected Condition getCondition() {
    return okToClose;
  }

  public boolean isTestMode() {
    return testMode;
  }

  public boolean isCloseOnSignal() {
    return closeOnSignal;
  }

}
