package gov.ca.cwds.rest.shiro;

import gov.ca.cwds.rest.MinimalApiConfiguration;
import java.util.Collection;
import javax.servlet.Filter;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.secnod.dropwizard.shiro.ShiroBundle;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class YamlShiroBundle<T extends MinimalApiConfiguration> extends ShiroBundle<T> {

  @Override
  protected ShiroConfiguration narrow(T configuration) {
    return configuration.getShiroConfiguration();
  }

  @Override
  protected Filter createFilter(final T configuration) {
    ShiroConfiguration shiroConfig = narrow(configuration);
    final IniWebEnvironment shiroEnv =
        new YamlIniWebEnvironment(configuration.getShiroConfiguration());
    shiroEnv.setConfigLocations(shiroConfig.iniConfigs());
    shiroEnv.init();

    AbstractShiroFilter shiroFilter = new AbstractShiroFilter() {
      @Override
      public void init() throws Exception {
        Collection<Realm> realms = createRealms(configuration);
        WebSecurityManager securityManager = realms.isEmpty()
            ? shiroEnv.getWebSecurityManager()
            : new DefaultWebSecurityManager(realms);
        setSecurityManager(securityManager);
        setFilterChainResolver(shiroEnv.getFilterChainResolver());
      }
    };
    return shiroFilter;
  }
}
