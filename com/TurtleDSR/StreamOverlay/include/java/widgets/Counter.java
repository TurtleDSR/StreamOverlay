package com.TurtleDSR.StreamOverlay.include.java.widgets;

import java.security.InvalidParameterException;

import com.TurtleDSR.StreamOverlay.include.java.config.converter.IntegerConverter;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.KeybindConverter;
import com.TurtleDSR.StreamOverlay.include.java.config.converter.StringConverter;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ConfigMap;

import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public final class Counter implements Widget {
  public int count; //count on the widget
  public String label; //label on the widget

  public Keybind increment;
  public Keybind decrement;

  private String id; //id or name of the widget

  private ConfigMap configs;
  private ConfigMap defaults;

  private WidgetPanel boundPanel;

  public Counter(String id, ConfigMap configs, ConfigMap defaults) {
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
    KeybindConverter keyConv = new KeybindConverter();

    if(configs.get(id, "type").equals("counter")) {
      try{count = (Integer)configs.get(id, "count", intConv);} catch(Exception e){count = (Integer)defaults.get("counter", "count", intConv);};
      label = configs.get(id, "label"); if(label == null) {label = defaults.get("counter", "label");}

      try{increment = (Keybind)configs.get(id, "increment", keyConv);} catch(Exception e){increment = (Keybind)defaults.get("counter", "increment", keyConv);};
      try{decrement = (Keybind)configs.get(id, "decrement", keyConv);} catch(Exception e){decrement = (Keybind)defaults.get("counter", "decrement", keyConv);};

      increment.parent = this;
      decrement.parent = this;
    } else {
      throw new InvalidParameterException();
    }
  }

  @Override
  public void updateConfigMap() {
    configs.set(id, "count", count + "");
    configs.set(id, "label", StringConverter.convertOutput(label));
    configs.set(id, "increment", KeybindConverter.convertOutput(increment));
    configs.set(id, "decrement", KeybindConverter.convertOutput(decrement));

    configs.writeConfigsToFile();
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
    if(increment != null && increment.update()) {count++;}
    if(decrement != null && decrement.update()) {count--;}

    if(boundPanel != null) {boundPanel.update();}
    Main.main.config.configPanelMap.get(id).update();
    updateConfigMap();
  }

  @Override
  public void bind(WidgetPanel panel) {
    boundPanel = panel;
  }
}
