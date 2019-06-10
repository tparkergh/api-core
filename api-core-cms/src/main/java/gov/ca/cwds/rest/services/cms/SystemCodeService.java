package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.rest.mapping.Mapper;
import gov.ca.cwds.rest.mapping.SystemCodeMapper;
import gov.ca.cwds.rest.mapping.SystemMetaMapper;
import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import gov.ca.cwds.rest.services.CrudsService;

/**
 * Business layer object to work on {@link SystemCode}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeService implements CrudsService {

  private final Mapper<gov.ca.cwds.data.persistence.cms.SystemCode, SystemCode> systemCodeMapper = new SystemCodeMapper();
  private final Mapper<SystemMeta, gov.ca.cwds.rest.api.domain.cms.SystemMeta> systemMetaMapper = new SystemMetaMapper();

  private SystemCodeDao systemCodeDao;
  private SystemMetaDao systemMetaDao;

  /**
   * Default no-arg constructor.
   */
  protected SystemCodeService() {}

  /**
   * 
   * @param systemCodeDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.SystemCode} objects.
   * @param systemMetaDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.SystemMeta} objects.
   */
  @Inject
  public SystemCodeService(SystemCodeDao systemCodeDao, SystemMetaDao systemMetaDao) {
    this.systemCodeDao = systemCodeDao;
    this.systemMetaDao = systemMetaDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    if (primaryKey == null || StringUtils.isBlank(primaryKey.toString())) {
      return loadSystemMetas();
    } else {
      return loadSystemCodesForMeta(primaryKey);
    }
  }

  protected Response loadSystemCode(Serializable systemCodeId) {
    if (systemCodeId == null) {
      return null;
    }

    Response response = null;
    gov.ca.cwds.data.persistence.cms.SystemCode systemCode =
        systemCodeDao.findBySystemCodeId((Number) systemCodeId);
    if (systemCode != null) {
      response = systemCodeMapper.map(systemCode);
    }
    return response;
  }

  /**
   * 
   * @param foreignKeyMetaTable the foreignKey to System Meta Table
   * @return the response containing List of Values from System Code Table that map to the
   *         primaryKey
   */
  protected Response loadSystemCodesForMeta(Serializable foreignKeyMetaTable) {
    if (foreignKeyMetaTable == null) {
      return null;
    }

    gov.ca.cwds.data.persistence.cms.SystemCode[] systemCodes =
        systemCodeDao.findByForeignKeyMetaTable(foreignKeyMetaTable.toString());

    Response response = null;

    if (systemCodes != null) {
      ImmutableSet.Builder<SystemCode> builder = ImmutableSet.builder();
      for (gov.ca.cwds.data.persistence.cms.SystemCode systemCode : systemCodes) {
        if (systemCode != null) {
          builder.add(systemCodeMapper.map(systemCode));
        }
      }
      Set<SystemCode> sysCodes = builder.build();
      if (!sysCodes.isEmpty()) {
        response = new SystemCodeListResponse(sysCodes);
      }
    }
    return response;
  }

  /**
   * 
   * @return the response containing List of Values from System Meta Table
   */
  protected Response loadSystemMetas() {
    Response response = null;
    SystemMeta[] sysMeta = systemMetaDao.findAll();

    if (sysMeta != null) {
      ImmutableSet.Builder<gov.ca.cwds.rest.api.domain.cms.SystemMeta> builder =
          ImmutableSet.builder();

      for (SystemMeta s : sysMeta) {
        if (s != null) {
          builder.add(systemMetaMapper.map(s));
        }
      }

      Set<gov.ca.cwds.rest.api.domain.cms.SystemMeta> systemMetas = builder.build();
      if (systemMetas != null) {
        response = new SystemMetaListResponse(builder.build());
      }
    }
    return response;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public SystemCode create(Request request) {
    assert request instanceof SystemCode;

    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public SystemCode update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof SystemCode;

    throw new NotImplementedException("Update is not implemented");
  }

}
