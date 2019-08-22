package gov.ca.cwds.rest.services;

import java.io.Serializable;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Interface for business {@link Service} which perform CRUDS operations. This interface strongly
 * types a service's primary key, request (input), and response (output).
 * 
 * @author CWDS API Team
 * @param <K> key type
 * @param <I> (input) request type
 * @param <O> (output) response type
 */
public interface TypedCrudsService<K extends Serializable, I extends Request, O extends Response>
    extends Service {

  /**
   * Find object by primaryKey
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to find.
   * 
   * @return The {@link Response} containing the found object, null if not found.
   */
  default O find(K primaryKey) {
    throw new NotImplementedException("Find is not implemented");
  }

  /**
   * Delete object by id
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to delete.
   * 
   * @return The {@link Response} containing the deleted object, null if not found.
   */
  default O delete(K primaryKey) {
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * Create object
   * 
   * @param request {@link Request} with a {@link DomainObject} to create.
   * 
   * @return The {@link Response}
   */
  default O create(I request) {
    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * Update object
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to update.
   * @param request {@link Request} with a {@link DomainObject} to update.
   * 
   * @return The {@link Response}
   */
  default O update(K primaryKey, I request) {
    throw new NotImplementedException("Update is not implemented");
  }

}
