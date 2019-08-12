package gov.ca.cwds.data.audit;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.DB2Connection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import gov.ca.cwds.security.realm.PerrySubject;

// This session factory is introduced exactly for CMS DB2 access monitoring
@SuppressFBWarnings("JVR_JDBC_VENDOR_RELIANCE")
public class CMSAuditingSessionFactory extends SessionFactoryDelegatingImpl implements Work {

  private static final Logger LOGGER = LoggerFactory.getLogger(CMSAuditingSessionFactory.class);

  public CMSAuditingSessionFactory(SessionFactoryImplementor delegate) {
    super(delegate);
  }

  @Override
  public Session openSession() throws HibernateException {
    LOGGER.info("CMSAuditingSessionFactory.openSession");
    final Session session = super.openSession();
    session.doWork(this);
    return session;
  }

  @Override
  public void execute(Connection connection) throws SQLException {
    LOGGER.info("CMSAuditingSessionFactory.execute");
    final DB2Connection db2Connection = (DB2Connection) connection;
    final String racfid = PerrySubject.getPerryAccount().getUser();
    // racfid will be available as CURRENT CLIENT_USERID
    // racfid will be available as "Client user ID" in Audit record layout for EXECUTE events
    // see:
    // https://www.ibm.com/support/knowledgecenter/en/SSEPGG_9.5.0/com.ibm.db2.luw.admin.sec.doc/doc/r0051526.html
    db2Connection.setDB2ClientUser(racfid);
  }

}
