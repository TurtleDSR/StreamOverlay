package com.TurtleDSR.StreamOverlay.include.java.config;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.*;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.ClockPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.CounterPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.LabelPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ClockConfigPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ConfigPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.CounterConfigPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.LabelConfigPanel;
import com.TurtleDSR.StreamOverlay.include.java.widgets.*;

import java.awt.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public final class ServerConfig {
  private ConfigMap configMap = new ConfigMap("com/TurtleDSR/StreamOverlay/config/config.dat");
  private ConfigMap defaultMap = new ConfigMap("com/TurtleDSR/StreamOverlay/config/default.dat");
  private ConfigMap widgetDefault = new ConfigMap("com/TurtleDSR/StreamOverlay/config/widgetDefault.dat");

  public Map<String, Widget> widgetMap = new HashMap<String, Widget>();
  public Map<String, WidgetPanel> panelMap = new HashMap<String, WidgetPanel>();
  public Map<String, ConfigPanel> configPanelMap= new HashMap<String, ConfigPanel>();

  public static final boolean RESETCONFIGS = true;
  public static final boolean DONOTRESETCONFIGS = false;

  public int port;

  public String foregroundColor;
  public float foregroundAlpha;

  public String backgroundColor;
  public float backgroundAlpha;

  public float fontSize;

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

    try {port = (Integer)configMap.get("server", "port", intConv);} catch(Exception e) {try{port = (Integer)defaultMap.get("server", "port", intConv);} catch (Exception ex) {throw ex;}}
    foregroundColor = configMap.get("server", "foregroundColor"); if(foregroundColor == null) foregroundColor = defaultMap.get("server", "foregroundColor");
    try {foregroundAlpha = (Float)configMap.get("server", "foregroundAlpha", floatConv);} catch(Exception e) {foregroundAlpha = (Float)defaultMap.get("server", "foregroundAlpha", floatConv);}
    backgroundColor = configMap.get("server", "backgroundColor"); if(backgroundColor == null) backgroundColor = defaultMap.get("server", "backgroundColor");
    try {backgroundAlpha = (Float)configMap.get("server", "backgroundAlpha", floatConv);} catch(Exception e) {backgroundAlpha = (Float)defaultMap.get("server", "backgroundAlpha", floatConv);}
    try {fontSize = (Float)configMap.get("server", "fontSize", floatConv);} catch(Exception e) {backgroundAlpha = (Float)defaultMap.get("server", "fontSize", floatConv);}

    Set<String> widgetKeys = configMap.getObjectKeys();
    for (String cur : widgetKeys) {
      addWidget(cur);
    }
  }

  private void addWidget(String cur) {
    if(!cur.equals("server")) {
      String type = configMap.get(cur, "type");

      if(type.equals("counter")) {
        Counter o = new Counter(cur, configMap, widgetDefault);

        widgetMap.put(cur, o);
        panelMap.put(cur, new CounterPanel((Counter)widgetMap.get(cur), Main.poppins.deriveFont(fontSize), this));
        configPanelMap.put(cur, new CounterConfigPanel(o));

      } else if(type.equals("label")) {
        Label o = new Label(cur, configMap, widgetDefault);

        widgetMap.put(cur, o);
        panelMap.put(cur, new LabelPanel(o, Main.poppins.deriveFont(fontSize), this));
        configPanelMap.put(cur, new LabelConfigPanel(o));

      } else if(type.equals("clock")) {
        Clock o = new Clock(cur, configMap, widgetDefault);

        widgetMap.put(cur, o);
        panelMap.put(cur, new ClockPanel(o, Main.poppins.deriveFont(fontSize), this));
        configPanelMap.put(cur, new ClockConfigPanel(o, Main.poppins.deriveFont(20f), this));
      }
    }
  }

  public void resetConfigs() {
    String path = configMap.getPath();
    configMap = defaultMap;

    defaultMap.writeConfigsToFile(path);
  }

  public static Color hextoColor(String hex) { //turns hexcode into rgb value and passes it into a new Color object
    return new Color(Integer.parseInt(hex.substring(1, hex.length()), 16));
  }
}
