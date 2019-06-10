package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

/**
 * Enumeration of legacy table names and human readable descriptions.
 * 
 * @author CWDS API Team
 */
public enum LegacyTable {

  //
  // CHECKSTYLE:OFF
  //

  ADULT_IN_PLACEMENT_HOME("OTH_ADLT", "Adult in placement home"),

  ADDRESS("ADDRS_T", "Address"),

  ADDRESS_PHONETIC("ADR_PHTT", "Address Phonetic"),

  ALIAS_OR_OTHER_CLIENT_NAME("OCL_NM_T", "Alias or other client name"),

  ALLEGATION("ALLGTN_T", "Allegation"),

  ASSIGNMENT("ASGNM_T", "Assignment"),

  ATTORNEY("ATTRNY_T", "Attorney"),

  CASE("CASE_T", "Case"),

  CHILD_IN_PLACEMENT_HOME("OTH_KIDT", "Child in placement home"),

  CLIENT("CLIENT_T", "Client"),

  CLIENT_ADDRESS("CL_ADDRT", "Client Address"),

  CLIENT_PHONETIC("CLT_PHTT", "Client Phonetic"),

  CLIENT_RELATIONSHIP("CLN_RELT", "Client Relationship"),

  COLLATERAL_INDIVIDUAL("COLTRL_T", "Collateral individual"),

  CROSS_REPORT("CRSS_RPT", "Cross Report"),

  CSEC_HISTORY("CSECHIST", "CSEC History"),

  DELIVERED_SERVICE("DL_SVC_T", "Delivered Service"),

  EDUCATION_PROVIDER("ED_PVDRT", "Education Provider"),

  EDUCATION_PROVIDER_CONTACT("EDPRVCNT", "Education Provider Contact"),

  GOVERNMENT_ORGANIZATION_ENTITY("GVR_ENTC", "Government Organization Entity"),

  LONG_TEXT("LONG_TXT", "Long Text (notes)"),

  OTHER_CLIENT_NAME("OCL_NM_T", "Other Client Name"),

  OUT_OF_HOME_PLACEMENT("O_HM_PLT", "Out of Home Placement"),

  PLACEMENT_EPISODE("PLC_EPST", "Placement Episode"),

  PLACEMENT_HOME("PLC_HM_T", "Placement Home"),

  REFERRAL("REFERL_T", "Referral"),

  REFERRAL_CLIENT("REFR_CLT", "Referral Client"),

  REPORTER("REPTR_T", "Reporter"),

  SAFETY_ALERT("SAF_ALRT", "Safety Alert"),

  SERVICE_PROVIDER("SVC_PVRT", "Service provider"),

  STAFF_PERSON("STFPERST", "Staff"),

  SUBSTITUTE_CARE_PROVIDER("SB_PVDRT", "Substitute care provider"),

  SUBSTITUTE_CARE_PROVIDER_PHONETIC("SCP_PHTT", "Substitute care provider phonetic"),

  ;

  //
  // CHECKSTYLE:ON
  //

  private String name;
  private String description;

  /**
   * Construct with table name and description.
   * 
   * @param name Legacy table name
   * @param description Legacy table human readable description.
   */
  private LegacyTable(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Get Legacy table name.
   * 
   * @return Legacy table name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get legacy table human readable description.
   * 
   * @return description of this legacy table
   */
  public String getDescription() {
    return description;
  }

  /**
   * Lookup a legacy table by table name.
   * 
   * @param tableName The legacy table name
   * @return LegacyTable for given name if found, null otherwise.
   */
  public static LegacyTable lookupByName(String tableName) {
    if (StringUtils.isBlank(tableName)) {
      return null;
    }

    LegacyTable legacyTable = null;
    for (LegacyTable lt : LegacyTable.values()) {
      if (lt.getName().equals(tableName.trim())) {
        legacyTable = lt;
        break;
      }
    }
    return legacyTable;
  }
}
