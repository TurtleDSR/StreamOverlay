package com.TurtleDSR.StreamOverlay.include.java.gui.color;

import javax.swing.colorchooser.DefaultColorSelectionModel;

public class ValuedSelectionModel extends DefaultColorSelectionModel {
  public JColorPicker parent;

  public ValuedSelectionModel(JColorPicker parent) {
    super();
    this.parent = parent;
  }
}
