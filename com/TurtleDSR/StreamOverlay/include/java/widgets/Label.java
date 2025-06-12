package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;

import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public class Label implements Widget {
  public String text;

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
    configs.writeConfigsToFile();
  }

  @Override
  public String getWidgetProperties() {
    return text;
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

  @Override
  public void addKeybinds(Keybind[] keybinds) {}
}
