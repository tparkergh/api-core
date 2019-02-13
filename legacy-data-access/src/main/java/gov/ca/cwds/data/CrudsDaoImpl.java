package gov.ca.cwds.data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.PersistentObject;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * An implementation of {@link CrudsDao}. Class is final and is expected that other {@link Dao} will
 * contain this implementation and delegate.
 *
 * @param <T> The {@link PersistentObject} to perform CRUDS operations on
 * @author CWDS API Team
 */
public class CrudsDaoImpl<T extends PersistentObject> extends AbstractDAO<T>
  implements CrudsDao<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrudsDaoImpl.class);

  private SessionFactory sessionFactory;

  /**
   * Default constructor.
   *
   * @param sessionFactory Hibernate session factory
   */
  public CrudsDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  /**
   * Grab a session! If a current session is available, return it, else open a new session.
   *
   * @return usable session
   */
  public Session grabSession() {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
      LOGGER.info("Current session is unavailable, opening a new session", e);
    }

    return session;
  }

  /**
   * Join the current transaction or begin a new one, as needed.
   *
   * @param session active session
   * @return active or new transaction
   */
  public Transaction joinTransaction(Session session) {
    TransactionStatus[] transactionStatuses = {
      TransactionStatus.COMMITTING,
      TransactionStatus.COMMITTED,
      TransactionStatus.FAILED_COMMIT,
      TransactionStatus.MARKED_ROLLBACK,
      TransactionStatus.ROLLED_BACK,
      TransactionStatus.ROLLING_BACK
    };

    Transaction txn = session.getTransaction();
    txn = txn != null ? txn : session.beginTransaction();

    if (!txn.getRollbackOnly() && !txn.isActive()
      && !Arrays.asList(transactionStatuses).contains(txn.getStatus())) {
      LOGGER.debug("Begin **NEW** transaction");
      txn.begin();
      CaresStackUtils.logStack();
    }

    return txn;
  }

  /**
   * Find the default schema for the current datasource.
   *
   * @return default schema for this datasource
   */
  public String getCurrentSchema() {
    return (String) getSessionFactory().getProperties().get("hibernate.default_schema");
  }

  /**
   * Provided for <strong>convenience</strong>. Don't abuse this.
   */
  @Override
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * {@inheritDoc}
   *
   * @see gov.ca.cwds.data.CrudsDao#find(java.io.Serializable)
   */
  @Override
  public T find(Serializable primaryKey) {
    grabSession();
    return get(primaryKey);
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsDao#delete(java.io.Serializable)
   */
  @Override
  public T delete(Serializable id) {
    final Session session = grabSession();
    final T object = find(id);
    if (object != null) {
      session.delete(object);
    }
    return object;
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsDao#create(gov.ca.cwds.data.persistence.PersistentObject)
   */
  @Override
  public T create(T object) {
    grabSession();
    if (object.getPrimaryKey() != null) {
      final T databaseObject = find(object.getPrimaryKey());
      if (databaseObject != null) {
        final String msg = MessageFormat.format("entity with id={0} already exists", object);
        LOGGER.error(msg);
        throw new EntityExistsException(msg);
      }
    }
    return persist(object);
  }

  /**
   * {@inheritDoc}
   *
   * @see CrudsDao#update(gov.ca.cwds.data.persistence.PersistentObject)
   */
  @Override
  public T update(T object) {
    final Session session = grabSession();
    final T databaseObject = find(object.getPrimaryKey());
    if (databaseObject == null) {
      final String msg =
        MessageFormat.format("Unable to find entity with id={0}", object.getPrimaryKey());
      LOGGER.error(msg);
      throw new EntityNotFoundException(msg);
    }

    // DRS: HOT-2176: isolate "possible non-threadsafe access to session".
    session.flush();
    session.evict(databaseObject);
    return persist(object);
  }

}
