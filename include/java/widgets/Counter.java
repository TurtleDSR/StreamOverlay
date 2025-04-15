package include.java.widgets;

import java.security.InvalidParameterException;

import include.java.config.converter.IntegerConverter;
import include.java.config.ConfigMap;

public final class Counter implements Widget {
  public String id; //id or name of the widget
  public int count; //count on the widget
  public String label; //label on the widget

  private ConfigMap configs;
  private ConfigMap defaults;

  public Counter(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;
    readConfigData();
  }

  @Override
  public void readConfigData() {
    IntegerConverter intConv = new IntegerConverter();

    if(configs.get(id, "type").equals("counter")) {
      try{count = (Integer)configs.get(id, "count", intConv);} catch(Exception e){count = (Integer)defaults.get("counter", "count", intConv);};
      label = configs.get(id, "label"); if(label == null) {label = defaults.get("counter", "label");}
    } else {
      throw new InvalidParameterException();
    }
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "count", count + "");
    configs.set(id, "label", label);
  }

  @Override
  public String getWidgetProperties() {
    StringBuilder builder = new StringBuilder();

    builder.append(count + "\n")
    .append(label + "\n");

    return builder.toString();
  }
}
