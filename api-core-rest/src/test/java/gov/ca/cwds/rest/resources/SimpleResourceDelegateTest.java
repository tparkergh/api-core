package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.core.Response;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;


public class SimpleResourceDelegateTest {

  private static class TestApiRequest implements Request {

    private String something;

    public TestApiRequest(@Valid @NotNull String something) {
      this.something = something;
    }

    public String getSomething() {
      return something;
    }

    public void setSomething(String something) {
      this.something = something;
    }

  }

  private static class TestApiResponse implements gov.ca.cwds.rest.api.Response {

    @NotNull
    @Pattern(regexp = "\\w+")
    private String output;

    public TestApiResponse(@Valid @NotNull String output) {
      this.output = output;
    }

    public String getOutput() {
      return output;
    }

    public void setOutput(String output) {
      this.output = output;
    }

    @Override
    public boolean hasMessages() {
      return false;
    }

    @Override
    public ArrayList getMessages() {
      return null;
    }
  }

  private static class TestSimpleResourceDelegateImpl extends
      SimpleResourceDelegate<String, TestApiRequest, TestApiResponse, ApiSimpleResourceService<String, TestApiRequest, TestApiResponse>>
      implements
      ApiSimpleResourceDelegate<String, TestApiRequest, TestApiResponse, ApiSimpleResourceService<String, TestApiRequest, TestApiResponse>> {

    public TestSimpleResourceDelegateImpl(
        ApiSimpleResourceService<String, TestApiRequest, TestApiResponse> service) {
      super(service);
    }

    @Override
    public TestApiResponse execFind(String key) throws ApiException {
      return super.execFind(key);
    }

    @Override
    public TestApiResponse execHandle(TestApiRequest req) throws ApiException {
      return super.execHandle(req);
    }

  }

  @Mock
  private ApiSimpleResourceService<String, TestApiRequest, TestApiResponse> svc;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private SessionFactory sf;

  @InjectMocks
  @Spy
  private TestSimpleResourceDelegateImpl target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
    reset(svc);
  }

  @Test
  public void type() throws Exception {
    assertThat(SimpleResourceDelegate.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_A$Object() throws Exception {
    final String key = "abc";
    when(svc.find(key)).thenReturn(new TestApiResponse("nuttin"));
    Response actual = target.find(key);
    assertTrue(actual.getEntity() instanceof TestApiResponse);
  }

  @Test(expected = ServiceException.class)
  public void find_A$Object_T$ServiceException() throws Exception {
    final String key = "asdfasdf";
    when(svc.find(any())).thenThrow(new ServiceException(new IllegalArgumentException()));
    final Response response = target.find(key);
    assertTrue(response.getStatus() != Response.Status.OK.ordinal());
  }

  @Test
  public void getService_A$() throws Exception {
    assertTrue((target.getService() instanceof ApiSimpleResourceService<?, ?, ?>));
  }

  @Test
  public void handle_A$Object() throws Exception {
    TestApiRequest req = new TestApiRequest("hello");
    when(svc.handle(any())).thenReturn(new TestApiResponse("world"));
    Response actual = target.handle(req);
    assertTrue(actual.getEntity() instanceof TestApiResponse);
  }

  @Test(expected = ApiException.class)
  public void handle_A$Object_T$ServiceException() throws Exception {
    TestApiRequest req = new TestApiRequest("&*^(%*#");
    when(svc.handle(req))
        .thenThrow(new ApiException(new ServiceException(new IllegalArgumentException())));
    target.handle(req);
    final Response response = target.handle(req);
    assertTrue(response.getStatus() != Response.Status.OK.ordinal());
  }

  @Test(expected = IllegalArgumentException.class)
  public void handle_Args$Object() throws Exception {
    TestApiRequest req = null;
    Response actual = target.handle(req);
    Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void handle_Args$Object_Throws$ServiceException() throws Exception {
    TestApiRequest req = null;
    target.handle(req);
    fail("Expected exception was not thrown!");
  }

  @Test(expected = IllegalArgumentException.class)
  public void find_Args$Object_Throws$ServiceException() throws Exception {
    final String key = null;
    Response actual = target.find(key);
    Response expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void find_Args$Object_Throws$ApiException() throws Exception {
    final String key = "maryhadalittlelamb";
    when(svc.find(key)).thenReturn(new TestApiResponse("nuttin"));
    target.find(key);
  }

  @Test
  public void getService_Args$() throws Exception {
    Object actual = target.getService();
    Object expected = svc;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateResponse_Args$Object_T$_null_response() throws Exception {
    TestApiResponse resp = null;
    target.validateResponse(resp);
  }

  @Test(expected = ConstraintViolationException.class)
  public void validateResponse_Args$Object_Throws$ConstraintViolationException() throws Exception {
    TestApiResponse resp = new TestApiResponse("*&$ *#");
    target.validateResponse(resp);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void validateRequest_Args$Object() throws Exception {
    TestApiRequest req = new TestApiRequest(null);
    target.validateRequest(req);
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateRequest_Args$Object_Throws$_null_request() throws Exception {
    TestApiRequest req = null;
    target.validateRequest(req);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void validateKey_Args$String_valid() throws Exception {
    String key = "fred";
    target.validateKey(key);
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateKey_Args$Object_Throws$IllegalArgumentException() throws Exception {
    String key = null;
    target.validateKey(key);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void getTypeParams_Args$() throws Exception {
    Object[] actual = target.getTypeParams();
    Object[] expected = new TypeVariable<?>[0];
    assertThat(actual, is(equalTo(expected)));
  }

}
