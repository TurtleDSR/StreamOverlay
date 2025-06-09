package include.java.widgets;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import include.java.config.ConfigMap;
import include.java.config.converter.BooleanConverter;

public class Clock implements Widget{
  String id;

  boolean zone;
  boolean date;
  boolean time;

  ConfigMap configs;
  ConfigMap defaults;

  public Clock(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;

    readConfigData();
  }

  @Override
  public String getWidgetProperties() {
    return (zone ? "EDT" : "") + "\n" + (date ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy")) : "") + "\n" + (time ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) : "");
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "zone", zone ? "true" : "false");
    configs.set(id, "date", date ? "true" : "false");
    configs.set(id, "time", time ? "true" : "false");
  }

  @Override
  public void readConfigData() {
    BooleanConverter boolConv = new BooleanConverter();

    if(configs.get(id, "type").equals("clock")) {
      try{zone = (Boolean)configs.get(id, "zone", boolConv);} catch(Exception e){zone = (Boolean)defaults.get("clock", "zone", boolConv);};
      try{date = (Boolean)configs.get(id, "date", boolConv);} catch(Exception e){date = (Boolean)defaults.get("clock", "date", boolConv);};
      try{time = (Boolean)configs.get(id, "time", boolConv);} catch(Exception e){time = (Boolean)defaults.get("clock", "time", boolConv);};
    } else {
      throw new InvalidParameterException();
    }
  }
}
