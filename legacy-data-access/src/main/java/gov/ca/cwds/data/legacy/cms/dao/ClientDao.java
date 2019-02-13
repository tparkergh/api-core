package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.AccessType;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientCounty;
import gov.ca.cwds.data.stream.QueryCreator;
import gov.ca.cwds.data.stream.ScalarResultsStreamer;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */
public class ClientDao extends BaseDaoImpl<Client> {

  private static final Logger LOG = LoggerFactory.getLogger(ClientDao.class);

  private static final String CLIENTS_IDS_PARAM = "clientIds";
  private static final String STAFF_ID_PARAM = "staffId";
  private static final String NOW_PARAM = "now";

  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param facilityId facility primary key
   * @param childId    child primary key
   * @return child by facility id number and child id.
   */
  public Client findByFacilityIdAndChildId(String facilityId, String childId) {
    Client client = findSingleFacility("findByFacilityIdAndChildId", query -> {
      query.setParameter("facilityId", facilityId);
      query.setParameter("childId", childId);
    });
    if (client == null) {
      LOG.warn("There is no result for facilityId = {} and childId = {}", facilityId, childId);
    }
    return client;
  }

  /**
   * @param licenseNumber license number
   * @param childId       child primary key
   * @return child by license number and child id.
   */
  public Client findByLicNumAndChildId(String licenseNumber, String childId) {
    Client client = findSingleFacility("find", query -> {
      query.setParameter("licenseNumber", licenseNumber);
      query.setParameter("childId", childId);
    });
    if (client == null) {
      LOG.warn("There is no result for licenseNumber = {} and childId = {}", licenseNumber,
        childId);
    }
    return client;
  }

  /**
   * @param licenseNumber license number
   * @return stream of facilities found by string facility number.
   */
  public Stream<Client> streamByLicenseNumber(String licenseNumber) {
    QueryCreator<Client> queryCreator = (session, entityClass) -> session
      .createNamedQuery(entityClass.getSimpleName() + ".findAll", entityClass)
      .setParameter("licenseNumber", licenseNumber);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }

  /**
   * @param licenseNumber license number
   * @return stream of facilities found by integer license number.
   */
  public Stream<Client> streamByLicenseNumber(Integer licenseNumber) {
    return streamByLicenseNumber(String.valueOf(licenseNumber));
  }

  /**
   * @param facilityId facility primary key
   * @return stream of facilities found by facilityId.
   */
  public Stream<Client> streamByFacilityId(String facilityId) {
    QueryCreator<Client> queryCreator = (session, entityClass) -> session
      .createNamedQuery(entityClass.getSimpleName() + ".findByFacilityId", entityClass)
      .setParameter("facilityId", facilityId);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }

  /**
   * Gets access type by assignment
   *
   * @param clientId client identifier
   * @param staffId  staff person id
   * @return access type enum: {NONE, R, RW}
   */
  public AccessType getAccessTypeByAssignment(String clientId, String staffId) {
    return AccessType.valueOf(
      grabSession()
        .createNamedQuery(this.getEntityClass().getSimpleName() + ".getAccessTypeByAssignment")
        .setParameterList(CLIENTS_IDS_PARAM, Collections.singletonList(clientId))
        .setParameter(STAFF_ID_PARAM, staffId)
        .setParameter(NOW_PARAM, LocalDate.now())
        .uniqueResult().toString());
  }

  @SuppressWarnings("unchecked")
  public Collection<String> filterClientIdsByAssignment(Collection<String> clientIds,
                                                        String staffId) {
    if (clientIds.isEmpty()) {
      return clientIds;
    }
    return grabSession()
      .createNamedQuery(this.getEntityClass().getSimpleName() + ".filterClientIdsByAssignment")
      .setParameterList(CLIENTS_IDS_PARAM, clientIds)
      .setParameter(STAFF_ID_PARAM, staffId)
      .setParameter(NOW_PARAM, LocalDate.now()).list();
  }

  /**
   * Gets client access type by supervisor Id
   *
   * @param clientId          client identifier
   * @param supervisorStaffId supervisor staff person id
   * @return access type enum: {NONE, R, RW}
   */
  public AccessType getAccessTypeBySupervisor(String clientId, String supervisorStaffId) {
    return AccessType.valueOf(
      grabSession()
        .createNamedQuery(this.getEntityClass().getSimpleName() + ".getAccessTypeBySupervisor")
        .setParameterList(CLIENTS_IDS_PARAM, Collections.singletonList(clientId))
        .setParameter(STAFF_ID_PARAM, supervisorStaffId)
        .setParameter(NOW_PARAM, LocalDate.now())
        .uniqueResult().toString());
  }

  /**
   * Method returns Client county base on algorithm from IBM DocTool Rule R - 10672
   *
   * @param clientId 10 character Client Id
   * @return ClientCounty facade entity with client county information
   */
  public ClientCounty getClientCounty(String clientId) {
    Require.requireNotNullAndNotEmpty(clientId);
    List<ClientCounty> clientCounties = currentSession()
      .getNamedNativeQuery(ClientCounty.QUERY_CLIENT_COUNTY)
      .setResultSetMapping(ClientCounty.MAPPING_CLIENT_COUNTY)
      .setParameter("clientId", clientId)
      .setMaxResults(1)
      .getResultList();
    return clientCounties.isEmpty() ? new ClientCounty("4. NO COUNTY", 0, null, null, "") : clientCounties.get(0);
  }

  private Client findSingleFacility(String queryName, Consumer<Query<Client>> setParameters) {
    Session session = grabSession();
    Class<Client> entityClass = getEntityClass();
    Query<Client> query =
      session.createNamedQuery(entityClass.getSimpleName() + "." + queryName, entityClass);
    setParameters.accept(query);
    query.setMaxResults(1);
    Client client = null;
    try {
      client = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.debug(e.getMessage(), e);
    }

    return client;
  }

}
