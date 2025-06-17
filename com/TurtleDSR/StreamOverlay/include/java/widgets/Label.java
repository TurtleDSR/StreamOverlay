package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;

import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.IntegerConverter;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.StringConverter;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;

public class Label implements Widget {
  public String text;
  public int width;
  public int height;

  private String id;

  private ConfigMap configs;
  private ConfigMap defaults;

  private WidgetPanel boundPanel;

  public Label(String id, ConfigMap configs, ConfigMap defaults) {
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
  public void setId(String newId) {
    id = newId;
  }

  @Override
  public void readConfigData() {
    IntegerConverter intConv = new IntegerConverter();
    if(configs.get(id, "type").equals("label")) {
      text = configs.get(id, "text"); if(text == null) {text = defaults.get("label", "text");}
      try{width = (Integer) configs.get(id, "width", intConv);} catch (Exception e){width = (Integer)defaults.get("label", "width", intConv);};
      try{height = (Integer) configs.get(id, "height", intConv);} catch (Exception e){height = (Integer)defaults.get("label", "height", intConv);};
    } else {
      throw new InvalidParameterException();
    }
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "text", StringConverter.convertOutput(text));
    configs.set(id, "width", width + "");
    configs.set(id, "height", height + "");
    configs.writeConfigsToFile();
  }

  @Override
  public String getWidgetProperties() {
    return StringConverter.convertOutput(text) + "\n" + width + "\n" + height + "\n";
  }

  @Override
  public void bind(WidgetPanel panel) {
    boundPanel = panel;
  }

  @Override
  public void update() {
    boundPanel.update();
    updateConfigMap();
  }

  public String getTextAsHTML() {
    return "<html><p>" + text + "</p></html>";
  }
}
