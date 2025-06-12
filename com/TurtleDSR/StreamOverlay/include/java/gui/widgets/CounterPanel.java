package com.TurtleDSR.StreamOverlay.include.java.gui.widgets;

import javax.swing.*;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Counter;

public class CounterPanel extends JPanel {
  public Counter boundCounter;
  
  private ServerConfig config;

  private JLabel counterText;

  public CounterPanel(Counter counter, Font displayFont, ServerConfig config) {
    super(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(0, 0, 10, 0);

    this.config = config;

    boundCounter = counter;
    counter.bind(this);
    counterText = new JLabel(boundCounter.label + boundCounter.count);
    counterText.setFont(displayFont);

    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    counterText.setForeground(ServerConfig.hextoColor(config.foregroundColor));

    add(counterText, c);
  }

  public void updatePanel() {
    counterText.setText(boundCounter.label + boundCounter.count);

    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    counterText.setForeground(ServerConfig.hextoColor(config.foregroundColor));
  }
}
