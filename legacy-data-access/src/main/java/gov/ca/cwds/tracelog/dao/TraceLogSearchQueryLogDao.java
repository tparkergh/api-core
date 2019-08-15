package gov.ca.cwds.tracelog.dao;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.tracelog.entity.TraceLogSearchQueryLog;

public class TraceLogSearchQueryLogDao extends BaseDaoImpl<TraceLogSearchQueryLog> {

  @Inject
  public TraceLogSearchQueryLogDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
