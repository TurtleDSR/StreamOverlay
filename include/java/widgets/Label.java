package include.java.widgets;

import java.security.InvalidParameterException;

import include.java.config.ConfigMap;

public class Label implements Widget{
  public String id;
  public String text;

  private ConfigMap configs;
  private ConfigMap defaults;

  public Label(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;
    readConfigData();
  }

  @Override
  public void readConfigData() {
    if(configs.get(id, "type").equals("label")) {
      text = configs.get(id, "text"); if(text == null) {text = defaults.get("label", "text");}
    } else {
      throw new InvalidParameterException();
    }
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "text", text);
  }

  @Override
  public String getWidgetProperties() {
    return text;
  }
}
