package include.java.config;

import java.awt.Color;

public final class ServerConfig {
  private ConfigMap configMap = new ConfigMap("config/config.dat");
  private ConfigMap defaultMap = new ConfigMap("config/default.dat");

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
  }

  public void resetConfigs() {
    configMap = defaultMap;

    defaultMap.writeConfigsToFile("config/config.dat");
  }

  public static Color hextoColor(String hex) { //turns hexcode into rgb value and passes it into a new Color object
    return new Color(Integer.parseInt(hex.substring(1, hex.length()), 16));
  }

  static final class IntegerConverter implements TypeConverter { //converts string to Integer
    @Override
    public Object convertType(String input) {
      return Integer.parseInt(input);
    }
  }

  static final class FloatConverter implements TypeConverter{ //converts string to float
    @Override
    public Object convertType(String input) {
      return Float.parseFloat(input);
    }
  }
}
