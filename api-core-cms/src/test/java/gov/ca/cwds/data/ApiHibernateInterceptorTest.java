package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.SystemCode;

/**
 * @author CWDS API Team
 */
public class ApiHibernateInterceptorTest {

  ApiHibernateInterceptor target;

  @Before
  public void setup() throws Exception {
    target = new ApiHibernateInterceptor();
  }

  public void consume(PersistentObject obj) {
    // nuttin
  }

  @Test
  public void type() throws Exception {
    assertThat(ApiHibernateInterceptor.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void addHandler_Args__Class__Consumer() throws Exception {
    final Class klass = TestPersistentObject.class;
    Consumer<PersistentObject> consumer = mock(Consumer.class);
    ApiHibernateInterceptor.addHandler(klass, consumer);
  }

  @Test
  public void onDelete_Args__Object__Serializable__ObjectArray__StringArray__TypeArray()
      throws Exception {
    Object entity = new TestPersistentObject();
    Serializable id = "abc1234567";
    Object[] state = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    target.onDelete(entity, id, state, propertyNames, types);
  }

  @Test
  public void onFlushDirty_Args__Object__Serializable__ObjectArray__ObjectArray__StringArray__TypeArray()
      throws Exception {
    Object entity = new TestPersistentObject();
    Serializable id = "abc1234567";
    Object[] currentState = new Object[] {};
    Object[] previousState = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    boolean actual =
        target.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void onLoad_Args__Object__Serializable__ObjectArray__StringArray__TypeArray()
      throws Exception {
    Object entity = new TestPersistentObject();
    Serializable id = "abc1234567";
    Object[] state = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    boolean actual = target.onLoad(entity, id, state, propertyNames, types);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void onSave_Args__Object__Serializable__ObjectArray__StringArray__TypeArray()
      throws Exception {
    Object entity = new TestPersistentObject();
    Serializable id = "abc1234567";
    Object[] state = new Object[] {};
    String[] propertyNames = new String[] {};
    Type[] types = new Type[] {};
    boolean actual = target.onSave(entity, id, state, propertyNames, types);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void preFlush_Args__Iterator() throws Exception {
    Iterator iter = mock(Iterator.class);
    target.preFlush(iter);
  }

  @Test
  public void postFlush_Args__Iterator() throws Exception {
    Iterator iterator = mock(Iterator.class);
    target.postFlush(iterator);
  }

  @Test
  public void afterTransactionBegin_Args__Transaction() throws Exception {
    Transaction tx = mock(Transaction.class);
    target.afterTransactionBegin(tx);
  }

  @Test
  public void beforeTransactionCompletion_Args__Transaction() throws Exception {
    Transaction tx = mock(Transaction.class);
    target.beforeTransactionCompletion(tx);
  }

  @Test
  public void afterTransactionCompletion_Args__Transaction() throws Exception {
    Transaction tx = mock(Transaction.class);
    target.afterTransactionCompletion(tx);
  }

  @Test
  public void instantiate_Args__String__EntityMode__Serializable() throws Exception {
    String entityName = null;
    EntityMode entityMode = EntityMode.POJO;
    Serializable id = "abc1234567";
    Object actual = target.instantiate(entityName, entityMode, id);
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void testPostFlush() throws Exception {
    final List<PersistentObject> list = new ArrayList<>();
    list.add(new TestPersistentObject());
    Iterator<PersistentObject> iter = list.iterator();
    target.postFlush(iter);
  }

  @Test
  public void testPreFlush() throws Exception {
    final List<PersistentObject> list = new ArrayList<>();
    list.add(new TestPersistentObject());
    Iterator<PersistentObject> iter = list.iterator();
    target.preFlush(iter);
  }

  @Test
  public void testAddHandler() throws Exception {
    ApiHibernateInterceptor.addHandler(TestPersistentObject.class, this::consume);
  }

  @SuppressWarnings("unused")
  @Test
  public void testForSynchronizedPreFlush() throws Exception {
    final ExecutorService service = Executors.newFixedThreadPool(4);
    final ApiHibernateInterceptor interceptor = new ApiHibernateInterceptor();
    final HashMap<Integer, Object> map = new HashMap<>();
    SystemCode code1 = new SystemCode();
    SystemCode code2 = new SystemCode();
    SystemCode code3 = new SystemCode();
    SystemCode code4 = new SystemCode();
    map.put(1, code1);
    map.put(2, code2);
    map.put(3, code3);
    map.put(4, code4);
    Iterator<?> iter = map.entrySet().iterator();
    Future<Date> future1 = execCallable(service, iter, interceptor);
    Future<Date> future2 = execCallable(service, iter, interceptor);
    Future<Date> future3 = execCallable(service, iter, interceptor);
    Future<Date> future4 = execCallable(service, iter, interceptor);
  }

  protected Future<Date> execCallable(ExecutorService service, Iterator<?> iter,
      ApiHibernateInterceptor interceptor) {
    return service.submit(() -> {
      final long tid = Thread.currentThread().getId();
      System.out.println("Thread started.. Id is " + tid);
      interceptor.preFlush(iter);
      return null;
    });
  }

}
