package gov.ca.cwds.test.support;

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author CWDS TPT-2 Team */
@SuppressFBWarnings(value = "PMB_POSSIBLE_MEMORY_BLOAT", justification = "Class used only in tests")
public abstract class BasePerryV2TokenProvider<T extends AuthParams> implements TokenProvider<T> {

  public static final String TOKEN_PARAM_NAME = "token";
  private static final Logger LOG = LoggerFactory.getLogger(BasePerryV2TokenProvider.class);

  private static final String PATH_PERRY_AUTHN_LOGIN = "/perry/authn/login";
  private static final String PATH_PERRY_AUTHN_TOKEN = "/perry/authn/token";
  private static final String CALLBACK = "callback";
  private static final String ACCESS_CODE = "accessCode";

  private String perryUrl;
  private String loginFormTargetUrl;

  private Client client;

  private static final Map<String, String> tokenCache = new HashMap<>();

  BasePerryV2TokenProvider(Client client, String perryUrl, String loginFormTargetUrl) {
    this.client = client;
    this.perryUrl = perryUrl;
    this.loginFormTargetUrl = loginFormTargetUrl;
    LOG.info("BasePerryV2TokenProvider was created");
    LOG.info("BasePerryV2TokenProvider: perryUrl: [{}], loginFormTargetUrl: [{}]", perryUrl,
        loginFormTargetUrl);
  }

  abstract Form prepareLoginForm(T config);

  @Override
  public String doGetToken(T params) {
    String paramsToString = ToStringBuilder.reflectionToString(params, ToStringStyle.JSON_STYLE);
    return Optional.ofNullable(tokenCache.get(paramsToString)).orElseGet(() -> {
      String token = getTokenFromPerry(params);
      tokenCache.put(paramsToString, token);
      LOG.info("New token [{}] for params [{}]", token, paramsToString);
      return token;
    });
  }

  private String getTokenFromPerry(T params) {
    final Map<String, NewCookie> cookies = postLoginFormAndGetJSessionIdCookie(params);
    final String accessCode = getAccessCodeFromPerry(cookies);
    return getTokenFromPerry(accessCode);
  }

  private Map<String, NewCookie> postLoginFormAndGetJSessionIdCookie(T params) {
    Form loginForm = prepareLoginForm(params);
    final Entity<Form> entity = Entity.form(loginForm);
    final Response response = client.target(loginFormTargetUrl)
        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
        .request()
        .header(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
        .post(entity);
    LOG.info("PostLoginForm with result [{}]", response.getStatusInfo());
    return response.getCookies();
  }

  private String getAccessCodeFromPerry(Map<String, NewCookie> cookies) {
    final Builder request = client.target(perryUrl)
        .path(PATH_PERRY_AUTHN_LOGIN)
        .queryParam(CALLBACK, "http://localhost/")
        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
        .request();
    cookies.forEach((key, value) -> request.cookie(key, value.getValue()));
    final Response response = request.get();
    URI location = response.getLocation();
    LOG.info("Get AccessCode location [{}]", location);
    return parseAccessCode(location);
  }

  private String parseAccessCode(URI uri) {
    return Arrays.stream(uri.getQuery()
        .split("&"))
        .filter(paramStr -> paramStr.startsWith(ACCESS_CODE))
        .findFirst()
        .map(s -> s.substring(s.indexOf('=') + 1))
        .orElse(null);
  }

  private String getTokenFromPerry(String accessCode) {
    final Response response = client.target(perryUrl)
        .path(PATH_PERRY_AUTHN_TOKEN)
        .queryParam(ACCESS_CODE, accessCode)
        .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
        .request()
        .get();

    return response.readEntity(String.class);
  }


}
