package com.TurtleDSR.StreamOverlay.include.java.widgets;

import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public interface Widget {
  public void readConfigData();
  public void updateConfigMap();

  public String getWidgetProperties();

  public void update();
  public void addKeybinds(Keybind[] keybinds);
}
