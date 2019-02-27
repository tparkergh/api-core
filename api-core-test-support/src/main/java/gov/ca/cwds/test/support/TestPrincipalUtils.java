package gov.ca.cwds.test.support;

import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Arrays;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DelegatingSubject;

public class TestPrincipalUtils {

  private TestPrincipalUtils() {

  }

  public static void login(PerryAccount account) {
    SecurityUtils.setSecurityManager(securityManager(account));
  }

  @SuppressWarnings("squid:MaximumInheritanceDepth")
  private static DefaultSecurityManager securityManager(PerryAccount account) {
    return new DefaultSecurityManager() {
      @Override
      public Subject createSubject(SubjectContext context) {
        return new DelegatingSubject(
          new SimplePrincipalCollection(Arrays.asList("test", account), "test"), true,
          "test", null, this) {
        };
      }
    };
  }

  public static void logout() {
    SecurityUtils.setSecurityManager(null);
  }

}
