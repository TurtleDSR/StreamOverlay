package com.TurtleDSR.StreamOverlay.include.java.widgets;

import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;

public interface Widget {
  public String getId();
  public void setId(String newId);

  public void readConfigData();
  public void updateConfigMap();

  public String getWidgetProperties();

  public void bind(WidgetPanel panel);

  public void update();
}
