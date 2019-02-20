package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

public class CrudsDaoImplTest {

  private SessionFactory sessionFactory;
  private Session session;
  private Query query;
  private Transaction txn;

  private CrudsDaoImpl<TestPersistentObject> target;

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

    target = new CrudsDaoImpl<>(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(CrudsDaoImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getSessionFactory_Args__() throws Exception {
    SessionFactory actual = target.getSessionFactory();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void find_Args__Serializable() throws Exception {
    Serializable primaryKey = mock(Serializable.class);
    TestPersistentObject actual = target.find(primaryKey);
    TestPersistentObject expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void delete_Args__Serializable() throws Exception {
    Serializable id = mock(Serializable.class);
    TestPersistentObject actual = target.delete(id);
    TestPersistentObject expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void create_Args__Object() throws Exception {
    TestPersistentObject object = new TestPersistentObject("abc123");
    TestPersistentObject actual = target.create(object);
    TestPersistentObject expected = new TestPersistentObject("abc123");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = EntityNotFoundException.class)
  public void update_Args__Object() throws Exception {
    TestPersistentObject object = new TestPersistentObject("abc123");
    TestPersistentObject actual = target.update(object);
    TestPersistentObject expected = new TestPersistentObject("abc123");
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = HibernateException.class)
  public void update_Args__Object_HibernateException() throws Exception {
    when(sessionFactory.getCurrentSession()).thenThrow(new HibernateException("No CurrentSessionContext configured!"));
    TestPersistentObject object = new TestPersistentObject("abc123");
    TestPersistentObject actual = target.update(object);
    TestPersistentObject expected = new TestPersistentObject("abc123");
    assertThat(actual, is(equalTo(expected)));
  }


}
