package com.TurtleDSR.StreamOverlay.include.java.gui.widgets;

import java.awt.LayoutManager;

import javax.swing.JPanel;

public abstract class WidgetPanel extends JPanel{
  //JPanel Constructors
  public WidgetPanel() {
    super();
  }

  public WidgetPanel(boolean isDoubleBuffered) {
    super(isDoubleBuffered);
  }

  public WidgetPanel(LayoutManager layoutManager) {
    super(layoutManager);
  }

  public WidgetPanel(LayoutManager layoutManager, boolean isDoubleBuffered) {
    super(layoutManager, isDoubleBuffered);
  }
  
  abstract public void update();

  abstract public void setDisplayed(boolean displayed);
}
