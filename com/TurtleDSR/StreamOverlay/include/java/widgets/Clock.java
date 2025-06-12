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

  public boolean showZone;
  public boolean showDate;
  public boolean showTime;

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
  public String getId() {
    return id;
  }

  @Override
  public String getWidgetProperties() {
    update();
    return text;
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "zone", showZone ? "true" : "false");
    configs.set(id, "date", showDate ? "true" : "false");
    configs.set(id, "time", showTime ? "true" : "false");
  }

  @Override
  public void readConfigData() {
    BooleanConverter boolConv = new BooleanConverter();

    if(configs.get(id, "type").equals("clock")) {
      try{showZone = (Boolean)configs.get(id, "zone", boolConv);} catch(Exception e){showZone = (Boolean)defaults.get("clock", "zone", boolConv);};
      try{showDate = (Boolean)configs.get(id, "date", boolConv);} catch(Exception e){showDate = (Boolean)defaults.get("clock", "date", boolConv);};
      try{showTime = (Boolean)configs.get(id, "time", boolConv);} catch(Exception e){showTime = (Boolean)defaults.get("clock", "time", boolConv);};
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
    text = (showZone ? "EDT" : "") + "\n" + (showDate ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy")) : "") + "\n" + (showTime ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) : "");
    
    lines = 0;
    if(showZone) {lines++;}
    if(showDate) {lines++;}
    if(showTime) {lines++;}

    boundPanel.update();
    updateConfigMap();
  }

  @Override
  public void addKeybinds(Keybind[] keybinds) {}

  public String getTextAsHTML() {
    return "<html>" + (showZone ? "EDT" : "") + "<br>" + (showDate ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy")) : "") + "<br>" + (showTime ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")) : "") + "</html>";
  }

  public void setToggles(boolean zone, boolean date, boolean time) {
    showZone = zone;
    showDate = date;
    showTime = time;

    update();
  }
}
