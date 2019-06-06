package gov.ca.cwds.rest.mapping;

import gov.ca.cwds.rest.api.domain.cms.SystemMeta;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * CWDS J Team
 */
public class SystemMetaMapperTest {
  private SystemMetaMapper subject = new SystemMetaMapper();

  @Test
  public void testMap() throws Exception {
    String logicalTableDsdName = "logical table dsd name";
    String userTableName = "user table name";
    String shortDescriptionName = "short description name";

    gov.ca.cwds.data.persistence.cms.SystemMeta source = new gov.ca.cwds.data.persistence.cms.SystemMeta(
        logicalTableDsdName, userTableName, shortDescriptionName);

    SystemMeta target = subject.map(source);
    assertEquals(logicalTableDsdName, target.getLogicalTableDsdName());
    assertEquals(userTableName, target.getUserTableName());
    assertEquals(shortDescriptionName, target.getShortDescriptionName());
  }
}