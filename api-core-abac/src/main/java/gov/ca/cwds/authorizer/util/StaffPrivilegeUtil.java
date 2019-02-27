package gov.ca.cwds.authorizer.util;


import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * Utility class for StaffPrivileges.
 *
 * @author CWDS TPT-3 Team
 */
public final class StaffPrivilegeUtil {

  public static final String CWS_CASE_MANAGEMENT_SYSTEM = "CWS Case Management System";
  public static final String RESOURCE_MANAGEMENT = "Resource Management";
  public static final String ADOPTIONS = "Adoptions";
  public static final String STATE_OF_CALIFORNIA = "State of California";
  public static final String SENSITIVE_PERSONS = "Sensitive Persons";
  public static final String SEALED = "Sealed";
  public static final String RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT =
      "Resource Mgmt Placement Facility Maint";

  private StaffPrivilegeUtil() {}

  // TODO(dd): Consider moving this logic to Drools.

  /**
   * Convert privileges from PerryAccount to Set of StaffPrivilegeType.
   *
   * @param perryAccount PerryAccount
   * @return set of StaffPrivilegeType
   */
  @SuppressWarnings("squid:S135")
  public static Set<StaffPrivilegeType> toStaffPersonPrivilegeTypes(
      final PerryAccount perryAccount) {
    final Set<String> privileges = perryAccount.getPrivileges();
    if (privileges == null || privileges.isEmpty()) {
      return Collections.emptySet();
    }

    final String countyName = perryAccount.getCountyName();
    final EnumSet<StaffPrivilegeType> results = EnumSet.noneOf(StaffPrivilegeType.class);
    for (final String privilege : privileges) {
      if (RESOURCE_MANAGEMENT.equalsIgnoreCase(privilege)) {
        results.add(StaffPrivilegeType.RESOURCE_MANAGEMENT);
        continue;
      }

      if (ADOPTIONS.equalsIgnoreCase(privilege)) {
        results.add(StaffPrivilegeType.ADOPTIONS);
        continue;
      }

      if (CWS_CASE_MANAGEMENT_SYSTEM.equals(privilege)) {
        results.add(StaffPrivilegeType.SOCIAL_WORKER_ONLY);
        continue;
      }

      if (RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT.equals(privilege)) {
        results.add(StaffPrivilegeType.RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
        continue;
      }

      if (STATE_OF_CALIFORNIA.equals(countyName)) {
        addStateLevelPrivilegeIfNeeded(results, privilege);
      } else {
        addCountyLevelPrivilegeIfNeeded(results, privilege);
      }
    }
    return results;
  }

  private static void addStateLevelPrivilegeIfNeeded(final Set<StaffPrivilegeType> results,
      final String privilege) {
    if (SENSITIVE_PERSONS.equals(privilege)) {
      results.add(StaffPrivilegeType.STATE_SENSITIVE);
    } else if (SEALED.equals(privilege)) {
      results.add(StaffPrivilegeType.STATE_SEALED);
    }
  }

  private static void addCountyLevelPrivilegeIfNeeded(final Set<StaffPrivilegeType> results,
      final String privilege) {
    if (SENSITIVE_PERSONS.equals(privilege)) {
      results.add(StaffPrivilegeType.COUNTY_SENSITIVE);
    } else if (SEALED.equals(privilege)) {
      results.add(StaffPrivilegeType.COUNTY_SEALED);
    }
  }
}
