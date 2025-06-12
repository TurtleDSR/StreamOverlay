package com.TurtleDSR.StreamOverlay.include.java.widgets;

import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public interface Widget {
  public String getId();

  public void readConfigData();
  public void updateConfigMap();

  public String getWidgetProperties();

  public void bind(WidgetPanel panel);

  public void update();
  public void addKeybinds(Keybind[] keybinds);
}
