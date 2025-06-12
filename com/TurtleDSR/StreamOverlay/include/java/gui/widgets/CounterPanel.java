package com.TurtleDSR.StreamOverlay.include.java.gui.widgets;

import javax.swing.*;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Counter;

public class CounterPanel extends WidgetPanel {

  public Counter boundCounter;
  
  private ServerConfig config;
  private JLabel counterText;

  private boolean displayed = false;

  public CounterPanel(Counter counter, Font displayFont, ServerConfig config) {
    super(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(0, 0, 10, 0);

    this.config = config;

    boundCounter = counter;
    counter.bind(this);

    counterText = new JLabel();
    counterText.setFont(displayFont);

    add(counterText, c);

    update();
  }

  @Override
  public void update() {
    counterText.setText(boundCounter.label + boundCounter.count);

    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    counterText.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    
    if(displayed) {Main.main.setSize(275, 75);}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
    update();
  }
}
