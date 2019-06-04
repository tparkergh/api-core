package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author CWDS CALS API Team
 *
 */
// Entity can't be splited , LocalDate is serializable
/**
 * @author CWDS CALS API Team
 */
@NamedQueries({
  @NamedQuery(
    name = "PlacementHome.find",
    query = "SELECT ph FROM gov.ca.cwds.data.legacy.cms.entity.PlacementHome ph "
      + " LEFT OUTER JOIN FETCH ph.countyLicenseCase cls"
      + " LEFT OUTER JOIN FETCH cls.staffPerson sp"
      + " LEFT OUTER JOIN FETCH cls.licensingVisits lv"
      + " WHERE ph.identifier = :facilityId"
  ),
  @NamedQuery(
    name = PlacementHome.FIND_LICENSE_NUMBER_BY_FACILITY_ID_QUERY_NAME,
    query = "SELECT ph.licenseNo FROM gov.ca.cwds.data.legacy.cms.entity.PlacementHome ph "
      + " WHERE ph.identifier = :facilityId"
  )}
)
@Entity
@Table(name = "PLC_HM_T")
public class PlacementHome extends BasePlacementHome {

  public static final String FIND_LICENSE_NUMBER_BY_FACILITY_ID_QUERY_NAME =
    "PlacementHome.findLicenseNumberByFacilityIdQueryName";

}
