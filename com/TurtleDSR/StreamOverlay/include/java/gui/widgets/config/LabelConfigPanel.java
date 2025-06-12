package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Label;

public class LabelConfigPanel extends ConfigPanel{
  Label boundLabel;

  public LabelConfigPanel(Label label) {
    boundLabel = label;
  }

  @Override
  public void update() {
    
  }

  @Override
  public void setDisplayed(boolean displayed) {
    
  }

  @Override
  public Widget getBoundWidget() {
    return boundLabel;
  }
}
