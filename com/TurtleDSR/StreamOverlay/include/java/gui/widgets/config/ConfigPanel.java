package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import javax.swing.JPanel;

import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

import java.awt.LayoutManager;

public abstract class ConfigPanel extends JPanel{
  //JPanel Constructors
  public ConfigPanel() {
    super();
  }

  public ConfigPanel(boolean isDoubleBuffered) {
    super(isDoubleBuffered);
  }

  public ConfigPanel(LayoutManager layoutManager) {
    super(layoutManager);
  }

  public ConfigPanel(LayoutManager layoutManager, boolean isDoubleBuffered) {
    super(layoutManager, isDoubleBuffered);
  }

  public abstract void update();
  public abstract void setDisplayed(boolean displayed);

  abstract public Widget getBoundWidget();
}
