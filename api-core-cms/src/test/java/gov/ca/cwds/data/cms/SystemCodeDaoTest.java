package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.common.CaresTestHarness;
import gov.ca.cwds.data.persistence.cms.SystemCode;

public class SystemCodeDaoTest extends CaresTestHarness<SystemCode> {

  SystemCodeDao target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new SystemCodeDao(sessionFactory);
  }

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void findByForeignKeyMetaTable() throws Exception {
    final SystemCode[] actual = target.findByForeignKeyMetaTable("asdf");
    final SystemCode[] expected = new SystemCode[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void findBySystemCodeId() throws Exception {
    final SystemCode actual = target.findBySystemCodeId(1234);
    final SystemCode expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void checkTransaction() throws Exception {
    final Session session = target.grabSession();
    Transaction actual = target.joinTransaction(session);
    assertNotNull(actual);
  }

}
