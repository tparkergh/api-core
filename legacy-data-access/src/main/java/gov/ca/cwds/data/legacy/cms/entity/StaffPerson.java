package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.entity.facade.ClientIdsByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

/**
 * StaffPerson entity
 *
 * @author CWDS CALS API Team
 */
@Entity
@javax.persistence.Table(name = "STFPERST")
@NamedNativeQueries({
  @NamedNativeQuery(
      name = StaffBySupervisor.NATIVE_FIND_STAFF_BY_SUPERVISOR_ID,
      query =
          "SELECT DISTINCT "
              + "  trim(user_id.LOGON_ID) AS racfId, "
              + "  stf.IDENTIFIER AS identifier, "
              + "  trim(stf.FIRST_NM) AS firstName, "
              + "  trim(stf.LAST_NM) AS lastName, "
              + "  stf.PHONE_NO AS phoneNumber, "
              + "  trim(stf.EMAIL_ADDR) AS email "
              + "FROM {h-schema}STFPERST stf "
              + "  LEFT JOIN {h-schema}STFUATHT a ON a.FKSTFPERST = stf.IDENTIFIER "
              + "  LEFT JOIN {h-schema}USERID_T user_id ON user_id.FKSTFPERST = stf.IDENTIFIER "
              + "WHERE a.FKASG_UNIT IN ( "
              + "  SELECT DISTINCT u.IDENTIFIER FROM {h-schema}ASG_UNIT u "
              + "      LEFT JOIN {h-schema}STFUATHT a ON a.FKASG_UNIT = u.IDENTIFIER "
              + "    WHERE a.UNTAUTH_CD = 'S' "
              + "      AND a.END_DT IS NULL "
              + "      AND a.FKSTFPERST = ?1) "
              + "  AND a.UNTAUTH_CD <> 'S' "
              + "  AND stf.END_DT IS NULL"
              + "  AND a.END_DT IS NULL"),
  @NamedNativeQuery(
      name = ClientIdsByStaff.NATIVE_FIND_CLIENT_IDS_BY_STAFF_IDS,
      query =
          "SELECT "
              + "    caseloadweight.FKSTFPERST AS staffIdentifier, "
              + "    (CASE WHEN case_.FKCHLD_CLT IS NOT NULL THEN case_.FKCHLD_CLT "
              + "       ELSE allegation.FKCLIENT_T END) AS clientIdentifier "
              + "  FROM "
              + "    {h-schema}CASE_LDT caseload "
              + "    LEFT OUTER JOIN {h-schema}CSLDWGHT caseloadweight "
              + "      ON caseload.IDENTIFIER = caseloadweight.FKCASE_LDT "
              + "    LEFT OUTER JOIN {h-schema}ASGNM_T assignment_ "
              + "      ON caseload.IDENTIFIER=assignment_.FKCASE_LDT "
              + "    LEFT OUTER JOIN {h-schema}CASE_T case_ "
              + "      ON assignment_.ESTBLSH_ID=case_.IDENTIFIER AND assignment_.ESTBLSH_CD='C' "
              + "    LEFT OUTER JOIN {h-schema}REFERL_T referral "
              + "      ON assignment_.ESTBLSH_ID=referral.IDENTIFIER AND assignment_.ESTBLSH_CD='R' "
              + "    LEFT OUTER JOIN {h-schema}ALLGTN_T allegation "
              + "      ON allegation.FKREFERL_T = referral.IDENTIFIER "
              + "  WHERE "
              + "    caseloadweight.FKSTFPERST IN (?1) "
              + "    AND (case_.END_DT IS NULL OR case_.END_DT > ?2) "
              + "    AND (assignment_.END_DT IS NULL OR assignment_.END_DT > ?2) "
              + "    AND (referral.ORIGCLS_DT IS NULL OR referral.ORIGCLS_DT > ?2) "
              + "    AND (allegation.DISPSN_DT IS NULL OR allegation.DISPSN_DT > ?2) "
              + "    AND (case_.FKCHLD_CLT IS NOT NULL OR allegation.FKCLIENT_T IS NOT NULL)")
})
@SqlResultSetMappings({
  @SqlResultSetMapping(
      name = StaffBySupervisor.MAPPING_STAFF_BY_SUPERVISOR,
      classes =
          @ConstructorResult(
              targetClass = StaffBySupervisor.class,
              columns = {
                @ColumnResult(name = "identifier"),
                @ColumnResult(name = "racfId"),
                @ColumnResult(name = "firstName"),
                @ColumnResult(name = "lastName"),
                @ColumnResult(name = "phoneNumber", type = String.class),
                @ColumnResult(name = "email")
              })),
  @SqlResultSetMapping(
      name = ClientIdsByStaff.MAPPING,
      classes =
          @ConstructorResult(
              targetClass = ClientIdsByStaff.class,
              columns = {
                @ColumnResult(name = "staffIdentifier", type = String.class),
                @ColumnResult(name = "clientIdentifier", type = String.class)
              }))
})
public class StaffPerson extends BaseStaffPerson {

  private static final long serialVersionUID = 5518501308828983602L;
}
