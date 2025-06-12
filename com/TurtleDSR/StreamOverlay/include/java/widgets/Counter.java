package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;

import com.TurtleDSR.StreamOverlay.include.java.config.converter.IntegerConverter;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.CounterPanel;
import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;

import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public final class Counter implements Widget {
  public String id; //id or name of the widget
  public int count; //count on the widget
  public String label; //label on the widget

  private Keybind increment;
  private Keybind decrement;

  private ConfigMap configs;
  private ConfigMap defaults;

  private CounterPanel boundPanel;

  public Counter(String id, ConfigMap configs, ConfigMap defaults) {
    this.id = id;
    this.configs = configs;
    this.defaults = defaults;
    readConfigData();
  }

  @Override
  public void readConfigData() {
    IntegerConverter intConv = new IntegerConverter();

    if(configs.get(id, "type").equals("counter")) {
      try{count = (Integer)configs.get(id, "count", intConv);} catch(Exception e){count = (Integer)defaults.get("counter", "count", intConv);};
      label = configs.get(id, "label"); if(label == null) {label = defaults.get("counter", "label");}
    } else {
      throw new InvalidParameterException();
    }
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "count", count + "");
    configs.set(id, "label", label);
  }

  @Override
  public String getWidgetProperties() {
    StringBuilder builder = new StringBuilder();

    builder.append(count + "\n")
    .append(label + "\n");

    return builder.toString();
  }

  @Override
  public void update() {
    if(increment != null && increment.update()) {
      count++;
      if(boundPanel != null) {boundPanel.updatePanel();}
    }
    if(decrement != null && decrement.update()) {
      count--;
      if(boundPanel != null) {boundPanel.updatePanel();}
    }
  }

  @Override
  public void addKeybinds(Keybind[] keybinds) {
    if(keybinds.length >= 0 && keybinds[0] != null) {increment = keybinds[0]; increment.parent = this;}
    if(keybinds.length >= 1 && keybinds[1] != null) {decrement = keybinds[1]; decrement.parent = this;}
  }

  public void bind(CounterPanel panel) {
    boundPanel = panel;
  }
}
