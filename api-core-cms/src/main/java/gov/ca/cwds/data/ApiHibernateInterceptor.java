package gov.ca.cwds.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.persistence.AccessLimitationAware;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.tracelog.core.TraceLogRequestContext;
import gov.ca.cwds.tracelog.core.TraceLogService;
import gov.ca.cwds.tracelog.simple.SimpleTraceLogService;

/**
 * Hibernate interceptor that logs SQL activity and traps referential integrity (RI) errors as a
 * <strong>last resort</strong>.
 * 
 * <p>
 * Validate any other "last ditch" constraints or business rules here before committing a
 * transaction to the database. Register handlers with method {@link #addHandler(Class, Consumer)}.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
 * private Client client;
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * Boolean return methods should return true <i>only</i> if the interceptor changes the object,
 * which it <i>does not yet</i> do.
 * </p>
 * 
 * @author CWDS API Team
 * @see ApiReferentialCheck
 */
@SuppressWarnings({"fb-contrib:PMB_POSSIBLE_MEMORY_BLOAT"})
public class ApiHibernateInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiHibernateInterceptor.class);

  /**
   * Hibernate events to handle.
   */
  public enum ApiHibernateEvent {
    BEFORE_COMMIT, AFTER_COMMIT, SAVE, LOAD, DELETE, AFTER_TXN_BEGIN, BEFORE_TXN_COMPLETE, AFTER_TXN_COMPLETE;
  }

  private static final Map<Class<? extends PersistentObject>, Consumer<PersistentObject>> handlers =
      new ConcurrentHashMap<>();

  private final TraceLogService traceLogService;

  /**
   * Ctor with basic Trace Log service.
   */
  public ApiHibernateInterceptor() {
    super();
    traceLogService = new SimpleTraceLogService();
  }

  @Inject
  public ApiHibernateInterceptor(TraceLogService traceLogService) {
    super();
    this.traceLogService = traceLogService;
  }

  /**
   * Register an RI handler by entity.
   * 
   * @param klass entity class to handle
   * @param consumer handler implementation
   */
  public static void addHandler(Class<? extends PersistentObject> klass,
      Consumer<PersistentObject> consumer) {
    LOGGER.debug("addHandler -> class={}", klass.getName());
    handlers.put(klass, consumer);
  }

  /**
   * Log access to database records.
   * 
   * @param entity Hibernate entity instance
   * @param id primary key
   * @param action CRUD action
   */
  protected void logAccess(Object entity, Serializable id, String action) {
    LOGGER.debug("{} -> id={}, entityClass={}", action, id, entity.getClass().getName());
    final String userId = TraceLogRequestContext.instance().getUserId();
    if (id != null) {
      traceLogService.logRecordAccess(userId, entity, id.toString());
    }
  }

  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    logAccess(entity, id, "D");
    logLimitedAccessRecord(entity, "onDelete");
  }

  /**
   * Called on entity update.
   */
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {
    logAccess(entity, id, "U");
    logLimitedAccessRecord(entity, "onFlushDirty");
    return false; // object state not changed
  }

  /**
   * Called on entity load.
   */
  @Override
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    logAccess(entity, id, "R");
    logLimitedAccessRecord(entity, "onLoad");
    return false; // object state not changed
  }

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    logAccess(entity, id, "C");
    logLimitedAccessRecord(entity, "onSave");
    return false; // object state not changed
  }

  /**
   * Called <strong>before</strong> the transaction commits.
   * 
   * <p>
   * The underlying iterator's container may share records with other threads, though those records
   * are not visible here. To avoid concurrent modification exceptions, this method synchronizes
   * briefly, converts the iterator to a list, and iterates the list.
   * </p>
   */
  @Override
  @SuppressWarnings("rawtypes")
  public void preFlush(Iterator iter) {
    LOGGER.trace("preFlush");
    CaresStackUtils.logStack();
    final List list = iterToList(iter);
    for (Object obj : list) {

      if (obj instanceof PersistentObject) { // our object type
        final PersistentObject entity = (PersistentObject) obj;
        logLimitedAccessRecord(obj, "preFlush");
        final Class<?> klazz = entity.getClass();

        if (handlers.containsKey(klazz)) {
          LOGGER.trace("handler for class {}", klazz);
          handlers.get(klazz).accept(entity);
        }

      }
    }
  }

  /**
   * Called <strong>after</strong> the transaction commits.
   */
  @Override
  public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) {
    LOGGER.trace("postFlush");
    CaresStackUtils.logStack();
  }

  @Override
  public void afterTransactionBegin(Transaction tx) {
    LOGGER.trace("afterTransactionBegin");
    CaresStackUtils.logStack();
    if (tx != null) {
      LOGGER.trace("afterTransactionBegin -> txn status={}", tx.getStatus());
    }
    super.afterTransactionBegin(tx);
  }

  @Override
  public void beforeTransactionCompletion(Transaction tx) {
    LOGGER.trace("****** before transaction completion ******");
    CaresStackUtils.logStack();
    if (tx != null) {
      LOGGER.trace("beforeTransactionCompletion -> txn status={}", tx.getStatus());
    }
    super.beforeTransactionCompletion(tx);
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    LOGGER.trace("****** after transaction completion ******");
    CaresStackUtils.logStack();
    if (tx != null) {
      LOGGER.trace("afterTransactionCompletion -> txn status={}", tx.getStatus());
    }
    super.afterTransactionCompletion(tx);
  }

  @Override
  public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {
    LOGGER.trace("instantiate -> id={}, entityClass={}", id, entityName);
    return super.instantiate(entityName, entityMode, id);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private synchronized List<?> iterToList(Iterator iter) {
    LOGGER.trace("iterToList");
    return IteratorUtils.toList(iter);
  }

  private static boolean logLimitedAccessRecord(Object obj, String operation) {
    boolean logged = false;
    if (obj instanceof PersistentObject && obj instanceof AccessLimitationAware) {
      final String limitedAccessCode = ((AccessLimitationAware) obj).getLimitedAccessCode();
      if (StringUtils.isNotBlank(limitedAccessCode) && !"N".equalsIgnoreCase(limitedAccessCode)) {
        LOGGER.trace(operation, " -> id= {}, entityClass= {}, sealed/sensitive= {}",
            ((PersistentObject) obj).getPrimaryKey(), obj.getClass().getName(), limitedAccessCode);
        logged = true;
      }
    }
    return logged;
  }

}
