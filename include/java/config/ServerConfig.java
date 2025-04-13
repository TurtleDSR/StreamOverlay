package include.java.config;

import java.io.*;

import java.awt.Color;

public final class ServerConfig {
  private ConfigMap configMap = new ConfigMap("config/config.dat");
  private ConfigMap defaultMap = new ConfigMap("config/default.dat");

  public static final boolean RESETCONFIGS = true;
  public static final boolean DONOTRESETCONFIGS = false;

  public int port;

  public int runCount;

  public String textColor;
  public float textOpacity;

  public String backgroundColor;
  public float backgroundOpacity;

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

    try {port = (Integer)configMap.getValue("port", intConv);} catch(Exception e) {try{port = (Integer)defaultMap.getValue("port", intConv);} catch (Exception ex) {}}
    try {runCount = (Integer)configMap.getValue("runCount", intConv);} catch(Exception e) {runCount = (Integer)defaultMap.getValue("runCount", intConv);}
    textColor = configMap.getValue("textColor"); if(textColor == null) textColor = defaultMap.getValue("textColor");
    try {textOpacity = (Float)configMap.getValue("textOpacity", floatConv);} catch(Exception e) {textOpacity = (Float)defaultMap.getValue("textOpacity", floatConv);}
    backgroundColor = configMap.getValue("backgroundColor"); if(backgroundColor == null) backgroundColor = defaultMap.getValue("backgroundColor");
    try {backgroundOpacity = (Float)configMap.getValue("backgroundOpacity", floatConv);} catch(Exception e) {backgroundOpacity = (Float)defaultMap.getValue("backgroundOpacity", floatConv);}
  }

  public boolean writeConfigs(ConfigMap map) { //returns false if anything fails
    try{FileWriter writer = new FileWriter(new File("config/config.dat"), false);
      writer.write(configsToString(map));
      writer.close();

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public boolean writeConfigs() { //returns false if anything fails
    try{FileWriter writer = new FileWriter(new File("config/config.dat"), false);
      writer.write(configsToString());
      writer.close();

      return true;
    } catch (IOException e) {
      try{
        FileWriter w = new FileWriter(new File("config/config.dat"));
        w.append(ConfigBuilder.defaultSettings());
        w.close();
      } catch(IOException ex) {
        System.err.println(ex.getMessage());
        System.exit(1);
      }
      return false;
    }
  }

  public void resetConfigs() {
    configMap = defaultMap;

    writeConfigs(defaultMap);
  }

  public String configsToString() { //returns a string of all configs separated by newlines
    ConfigBuilder builder = new ConfigBuilder();

    builder.appendConfig("port", (port + "")); //add all config variables to file
    builder.appendConfig("runCount", (runCount + ""));
    builder.appendConfig("textColor", textColor);
    builder.appendConfig("textOpacity", (textOpacity + ""));
    builder.appendConfig("backgroundColor", backgroundColor);
    builder.appendConfig("backgroundOpacity", (backgroundOpacity + ""));

    return builder.toString();
  }

  public String configsToString(ConfigMap map) { //returns a string of all configs separated by newlines
    ConfigBuilder builder = new ConfigBuilder();

    builder.appendConfig("port", map.getValue("port")); //add all config variables to file
    builder.appendConfig("runCount", map.getValue("runCount"));
    builder.appendConfig("textColor", map.getValue("textColor"));
    builder.appendConfig("textOpacity", map.getValue("textOpacity"));
    builder.appendConfig("backgroundColor", map.getValue("backgroundColor"));
    builder.appendConfig("backgroundOpacity", map.getValue("backgroundOpacity"));

    return builder.toString();
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
