package gov.ca.cwds.cms.data.access.mapper;

import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author CWDS CALS API Team
 */
@Mapper(imports = LocalDateTime.class)
@FunctionalInterface
public interface ExternalInterfaceMapper {

  @Mapping(target = "seqNo", constant = "-1")// TODO: 8/17/2017
  @Mapping(target = "submtlTs", expression = "java(LocalDateTime.now())")// TODO: 8/17/2017
  @Mapping(target = "tableName", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "operType", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey1", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey2", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey3", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey4", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey5", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey6", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey7", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "prmKey8", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "startDt", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "astUntCd", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "personNo", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "serialNo", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "aidTpc", constant = "-1")// TODO: 8/17/2017
  @Mapping(target = "gvrEntc", constant = "-1")// TODO: 8/17/2017
  @Mapping(target = "licenseNo", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "responsDt", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "receiveDt", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "rapId", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "fbiInd", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "crspTpc", constant = "-1")// TODO: 8/17/2017
  @Mapping(target = "otherData", constant = " ")// TODO: 8/17/2017
  @Mapping(target = "lUserid", constant = " ")// TODO: 8/17/2017
  ExternalInterface toExternalInterface(String stub);
}
