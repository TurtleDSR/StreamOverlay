package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.BooleanConverter;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public class Clock implements Widget{
  public String text;

  public boolean zone;
  public boolean date;
  public boolean time;

  public int lines;

  private String id;

  private ConfigMap configs;
  private ConfigMap defaults;

  private WidgetPanel boundPanel;

  public Clock(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;

    readConfigData();
  }

  @Override
  public String getWidgetProperties() {
    update();
    return text;
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

  @Override
  public void bind(WidgetPanel panel) {
    boundPanel = panel;
  }

  @Override
  public void update() {
    text = (zone ? "EDT" : "") + "\n" + (date ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy")) : "") + "\n" + (time ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) : "");
    
    lines = 0;
    if(zone) {lines++;}
    if(date) {lines++;}
    if(time) {lines++;}

    boundPanel.update();
    updateConfigMap();
  }

  @Override
  public void addKeybinds(Keybind[] keybinds) {}

  public String getTextAsHTML() {
    return "<html>" + (zone ? "EDT" : "") + "<br>" + (date ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy")) : "") + "<br>" + (time ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) : "") + "</html>";
  }
}
