package gov.ca.cwds.data;

import java.io.Serializable;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Interface for {@link Dao} which provide CRUDS operations
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link PersistentObject} the DAO implements CRUDS for
 */
public interface CrudsDao<T extends PersistentObject> extends Dao {

  /**
   * Find {@link PersistentObject} by id
   * 
   * @param primaryKey The primaryKey for this {@link PersistentObject}
   * 
   * @return The found object, null otherwise
   */
  public T find(Serializable primaryKey);

  /**
   * Delete {@link PersistentObject} by id
   * 
   * @param id The id of the {@link PersistentObject} to delete.
   * 
   * @return The deleted {@link PersistentObject}, null if not found
   */
  public T delete(Serializable id);

  /**
   * Create {@link PersistentObject}
   * 
   * @param object The {@link PersistentObject} to be created
   * 
   * @return The created {@link PersistentObject} with a newly populated id
   * 
   * @throws EntityExistsException when the {@link PersistentObject} already exists
   */
  public T create(T object) throws EntityExistsException;

  /**
   * Update {@link PersistentObject}
   * 
   * @param object The {@link PersistentObject} to be updated
   * 
   * @return The updated {@link PersistentObject}
   * 
   * @throws EntityNotFoundException when the {@link PersistentObject} does not exist
   */
  public T update(T object) throws EntityNotFoundException;

  /**
   * Get the SessionFactory associated with {@link PersistentObject}
   * 
   * @return The SessionFactory
   */
  public SessionFactory getSessionFactory();

}
