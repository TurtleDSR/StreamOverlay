package com.TurtleDSR.StreamOverlay.include.java.gui.settings;

import javax.swing.JTextField;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DefaultTextField extends JTextField implements FocusListener{
  private String defaultText;

  public DefaultTextField(String defaultText) {
    super(defaultText);
    this.defaultText = defaultText;
    addFocusListener(this);
  }

  public void setDefaultText(String text) {
    defaultText = text;
  }

  public String getDefaultText() {
    return defaultText;
  }

  @Override
  public void focusGained(FocusEvent e) {
    //selectAll();
  }

  @Override
  public void focusLost(FocusEvent e) {
    if(getText().equals("")) {
      setText(defaultText);
    }
  }
}
