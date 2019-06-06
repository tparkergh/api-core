package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElasticSearchQueryTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchQuery.class, notNullValue());
  }

  @Test
  public void getQueryFromTerm_Args__ssn() throws Exception {
    String term = "785-89-8913";
    ElasticSearchQuery actual = ElasticSearchQuery.getQueryFromTerm(term);
    ElasticSearchQuery expected = ElasticSearchQuery.SSN;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getQueryFromTerm_Args__name() throws Exception {
    String term = "fred";
    ElasticSearchQuery actual = ElasticSearchQuery.getQueryFromTerm(term);
    ElasticSearchQuery expected = ElasticSearchQuery.NAME;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getQueryFromTerm_Args__unknown() throws Exception {
    String term = "^%$#x";
    ElasticSearchQuery actual = ElasticSearchQuery.getQueryFromTerm(term);
    ElasticSearchQuery expected = ElasticSearchQuery.UNKNOWN;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getQueryFromTerm_Args__birth() throws Exception {
    String term = "12/31/2001";
    ElasticSearchQuery actual = ElasticSearchQuery.getQueryFromTerm(term);
    ElasticSearchQuery expected = ElasticSearchQuery.DOB;
    assertThat(actual, is(equalTo(expected)));
  }

}
