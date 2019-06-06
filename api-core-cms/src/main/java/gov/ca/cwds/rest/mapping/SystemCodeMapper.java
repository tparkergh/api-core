package gov.ca.cwds.rest.mapping;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;

/**
 * CWDS J Team
 */
public class SystemCodeMapper implements Mapper<gov.ca.cwds.data.persistence.cms.SystemCode, SystemCode> {
  @Override
  public SystemCode map(gov.ca.cwds.data.persistence.cms.SystemCode source) {
    SystemCode target = new SystemCode();

    target.setSystemId(source.getSystemId());
    target.setCategoryId(source.getCategoryId());
    target.setInactiveIndicator(source.getInactiveIndicator());
    target.setOtherCd(source.getOtherCd());
    target.setShortDescription(source.getShortDescription());
    target.setLogicalId(source.getLogicalId());
    target.setThirdId(source.getThirdId());
    target.setForeignKeyMetaTable(source.getForeignKeyMetaTable());
    target.setLongDescription(source.getLongDescription());

    return target;
  }
}
