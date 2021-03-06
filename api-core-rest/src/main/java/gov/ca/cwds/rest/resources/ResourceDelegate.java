package gov.ca.cwds.rest.resources;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Interface for Resource delegates. All resources should have at a minimum a GET, POST, PUT, and
 * DELETE. Those implementation should be fairly generic other than {@link Request} and
 * {@link Response} types.
 * 
 * @author CWDS API Team
 */
public interface ResourceDelegate {

  /**
   * Gets a {@link DomainObject} based on the given id.
   * 
   * @param id The id of the {@link DomainObject}
   * 
   * @return The {@link Response}
   */
  public Response get(Serializable id);

  /**
   * Delete a {@link DomainObject}
   * 
   * @param id The id of the {@link DomainObject}
   * 
   * @return {@link Response}
   */
  public Response delete(Serializable id);

  /**
   * Create a {@link DomainObject}
   * 
   * @param request The {@link Request}
   * 
   * @return The {@link Response}
   */
  public Response create(Request request);

  /**
   * Update a {@link DomainObject}
   *
   * @param id The id of the {@link DomainObject} to update.
   * @param request The {@link Request}
   *
   * @return The {@link Response}
   */
  public Response update(Serializable id, Request request);
}
