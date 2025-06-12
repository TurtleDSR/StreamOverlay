package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;

import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;

import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public class Label implements Widget{
  public String id;
  public String text;

  private ConfigMap configs;
  private ConfigMap defaults;

  public Label(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;
    readConfigData();
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
  }

  @Override
  public String getWidgetProperties() {
    return text;
  }

  @Override
  public void update() {}
  @Override
  public void addKeybinds(Keybind[] keybinds) {}
}
