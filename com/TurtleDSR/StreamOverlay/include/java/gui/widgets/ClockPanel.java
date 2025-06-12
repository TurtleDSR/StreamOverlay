package com.TurtleDSR.StreamOverlay.include.java.gui.widgets;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Clock;
import com.TurtleDSR.StreamOverlay.Main;

public class ClockPanel extends WidgetPanel {

  private Clock boundClock;
  private ServerConfig config;

  private JLabel label;

  private boolean displayed = false;

  public ClockPanel(Clock clock, Font font, ServerConfig config) {
    super(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(0, 0, 10, 0);

    this.boundClock = clock;
    this.config = config;

    boundClock.bind(this);

    label = new JLabel();
    label.setFont(font);

    add(label, c);
  }
  
  @Override
  public void update() {
    label.setText(boundClock.getTextAsHTML());

    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    label.setForeground(ServerConfig.hextoColor(config.foregroundColor));

    if(displayed) {Main.main.setSize(275, 60 * boundClock.lines);}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
    update();
  }
}
