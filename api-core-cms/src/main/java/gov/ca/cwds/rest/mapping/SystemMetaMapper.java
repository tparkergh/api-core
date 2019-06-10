package gov.ca.cwds.rest.mapping;

import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

/**
 * CWDS J Team
 */
public class SystemMetaMapper implements Mapper<gov.ca.cwds.data.persistence.cms.SystemMeta, SystemMeta> {
  @Override
  public SystemMeta map(gov.ca.cwds.data.persistence.cms.SystemMeta source) {
    SystemMeta target = new SystemMeta();
    target.setLogicalTableDsdName(source.getLogicalTableDsdName());
    target.setUserTableName(source.getUserTableName());
    target.setShortDescriptionName(source.getShortDescriptionName());
    return target;
  }
}
