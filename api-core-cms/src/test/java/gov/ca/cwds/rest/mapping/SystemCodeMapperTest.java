package gov.ca.cwds.rest.mapping;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS J Team
 */
public class SystemCodeMapperTest {
  private SystemCodeMapper subject = new SystemCodeMapper();


  @Test
  public void testMap() throws Exception {
    Short systemId = 1;
    Short categoryId = 2;
    String inactiveIndicator = "inactive indicator";
    String otherCd = "other cd";
    String shortDescription = "short description";
    String logicalId = "logical id";
    String thirdId = "third id";
    String foreignKeyMetaTable = "foreign key meta table";
    String longDescription = "long description";

    gov.ca.cwds.data.persistence.cms.SystemCode source =
      new gov.ca.cwds.data.persistence.cms.SystemCode(systemId, categoryId, inactiveIndicator,
        otherCd, shortDescription, logicalId, thirdId, foreignKeyMetaTable, longDescription);

    SystemCode target = subject.map(source);
    assertEquals(systemId, target.getSystemId());
    assertEquals(categoryId, target.getCategoryId());
    assertEquals(inactiveIndicator, target.getInactiveIndicator());
    assertEquals(otherCd, target.getOtherCd());
    assertEquals(shortDescription, target.getShortDescription());
    assertEquals(logicalId, target.getLogicalId());
    assertEquals(thirdId, target.getThirdId());
    assertEquals(foreignKeyMetaTable, target.getForeignKeyMetaTable());
    assertEquals(longDescription, target.getLongDescription());
  }
}