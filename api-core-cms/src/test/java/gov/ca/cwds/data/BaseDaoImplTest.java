package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BaseDaoImplTest {

  private static final class TestBaseDaoImpl extends BaseDaoImpl<TestPersistentObject> {

    public TestBaseDaoImpl(SessionFactory sessionFactory) {
      super(sessionFactory);
    }

  }

  private SessionFactory sessionFactory;
  private Session session;
  private Query query;
  private Transaction txn;

  private BaseDaoImpl target;

  @Before
  public void setup() {
    sessionFactory = mock(SessionFactory.class);
    session = mock(Session.class);
    query = mock(Query.class);
    txn = mock(Transaction.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);

    when(session.getNamedQuery(any(String.class))).thenReturn(query);
    when(session.beginTransaction()).thenReturn(txn);

    when(query.list()).thenReturn(new ArrayList<TestPersistentObject>());
    when(query.setString(any(String.class), any(String.class))).thenReturn(query);
    when(query.setInteger(any(String.class), any(Integer.class))).thenReturn(query);
    when(query.setTimestamp(any(String.class), any(Timestamp.class))).thenReturn(query);

    target = new TestBaseDaoImpl(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(BaseDaoImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findAll_Args__() throws Exception {
    List<TestPersistentObject> actual = target.findAll();
    List<TestPersistentObject> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void joinTransactionTest() {
    when(txn.getStatus()).thenReturn(TransactionStatus.ACTIVE);
    when(session.getTransaction()).thenReturn(txn);
    assertNotNull(target.joinTransaction(session));
  }

}
