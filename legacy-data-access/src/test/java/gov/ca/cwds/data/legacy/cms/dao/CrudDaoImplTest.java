package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrudDaoImplTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SessionFactory sessionFactory;
  private Session session;
  private Query query;
  private Transaction txn;

  private CrudsDaoImpl<Client> target;

  @Before
  public void setup() {
    sessionFactory = mock(SessionFactory.class);
    session = mock(Session.class);
    query = mock(Query.class);
    txn = mock(Transaction.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);

    when(session.getNamedQuery(any(String.class))).thenReturn(query);
    when(session.beginTransaction()).thenReturn(txn);

    when(query.list()).thenReturn(new ArrayList<Client>());
    when(query.setString(any(String.class), any(String.class))).thenReturn(query);
    when(query.setInteger(any(String.class), any(Integer.class))).thenReturn(query);
    when(query.setTimestamp(any(String.class), any(Timestamp.class))).thenReturn(query);

    target = new CrudsDaoImpl<>(sessionFactory);
  }

  @Test
  public void joinTransactionTest() {
    when(txn.getStatus()).thenReturn(TransactionStatus.ACTIVE);
    when(session.getTransaction()).thenReturn(txn);
    assertNotNull(target.joinTransaction(session));
  }

  @Test
  public void joinTransactionOldTest() {
    when(txn.getStatus()).thenReturn(TransactionStatus.COMMITTING);
    when(session.getTransaction()).thenReturn(txn);
    assertNotNull(target.joinTransaction(session));
  }
}
