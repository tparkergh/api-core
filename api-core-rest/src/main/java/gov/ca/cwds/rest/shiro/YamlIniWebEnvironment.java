package gov.ca.cwds.rest.shiro;

import gov.ca.cwds.rest.YamlShiroConfiguration;
import java.util.Optional;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.env.IniWebEnvironment;

class YamlIniWebEnvironment extends IniWebEnvironment {
  private YamlShiroConfiguration yamlShiroConfiguration;

  YamlIniWebEnvironment(YamlShiroConfiguration yamlShiroConfiguration) {
    this.yamlShiroConfiguration = yamlShiroConfiguration;
  }

  @Override
  protected void configure() {
    applyYamlPatch(getIni());
    super.configure();
  }

  private void applyYamlPatch(Ini ini) {
    Optional.ofNullable(yamlShiroConfiguration.getIni())
        .ifPresent(yamlIni ->
            yamlIni.forEach((group, props) ->
                props.forEach((key, value) -> {
                  ini.setSectionProperty(group, key, value);
                })));
  }
}
