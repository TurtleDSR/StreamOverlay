package include.java.config;

import include.java.config.converter.*;
import include.java.widgets.*;

import java.awt.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public final class ServerConfig {
  private ConfigMap configMap = new ConfigMap("config/config.dat");
  private ConfigMap defaultMap = new ConfigMap("config/default.dat");
  private ConfigMap widgetDefault = new ConfigMap("config/widgetDefault");

  public Map<String, Widget> widgetMap = new HashMap<String, Widget>();

  public static final boolean RESETCONFIGS = true;
  public static final boolean DONOTRESETCONFIGS = false;

  public int port;

  public String foregroundColor;
  public float foregroundAlpha;

  public String backgroundColor;
  public float backgroundAlpha;

  public ServerConfig(boolean resetConfigs) { //regenerates based on flag
    if(resetConfigs) {
      resetConfigs();
    } else {
      readConfigData();
    }
  }

  public ServerConfig() { //does not regenerate configs
    readConfigData();
  }

  public void readConfigData() {
    IntegerConverter intConv = new IntegerConverter();
    FloatConverter floatConv = new FloatConverter();

    try {port = (Integer)configMap.get("server", "port", intConv);} catch(Exception e) {try{port = (Integer)defaultMap.get("server", "port", intConv);} catch (Exception ex) {ConfigMap.rewriteConfigFiles();}}
    foregroundColor = configMap.get("server", "foregroundColor"); if(foregroundColor == null) foregroundColor = defaultMap.get("server", "foregroundColor");
    try {foregroundAlpha = (Float)configMap.get("server", "foregroundAlpha", floatConv);} catch(Exception e) {foregroundAlpha = (Float)defaultMap.get("server", "foregroundAlpha", floatConv);}
    backgroundColor = configMap.get("server", "backgroundColor"); if(backgroundColor == null) backgroundColor = defaultMap.get("server", "backgroundColor");
    try {backgroundAlpha = (Float)configMap.get("server", "backgroundAlpha", floatConv);} catch(Exception e) {backgroundAlpha = (Float)defaultMap.get("server", "backgroundAlpha", floatConv);}

    Set<String> widgetKeys = configMap.getObjectKeys();
    for (String cur : widgetKeys) {
      if(!cur.equals("server")) {
        String type = configMap.get(cur, "type");
        if(type.equals("counter")) {
          widgetMap.put(cur, new Counter(cur, configMap, widgetDefault));
        } else if(type.equals("label")) {
          widgetMap.put(cur, new Label(cur, configMap, widgetDefault));
        } else if(type.equals("clock")) {
          widgetMap.put(cur, new Clock(cur));
        }
      }
    }
  }

  public void resetConfigs() {
    configMap = defaultMap;

    defaultMap.writeConfigsToFile("config/config.dat");
  }

  public static Color hextoColor(String hex) { //turns hexcode into rgb value and passes it into a new Color object
    return new Color(Integer.parseInt(hex.substring(1, hex.length()), 16));
  }
}
