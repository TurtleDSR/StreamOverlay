package com.TurtleDSR.StreamOverlay.include.java.gui.widgets;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Label;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

public class LabelPanel extends WidgetPanel {

  private Label boundLabel;
  private ServerConfig config;

  private JLabel label;

  private boolean displayed = false;

  public LabelPanel(Label bindLabel, Font font, ServerConfig config) {
    super(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(0, 0, 10, 0);

    this.boundLabel = bindLabel;
    this.config = config;

    boundLabel.bind(this);

    label = new JLabel();
    label.setFont(font);

    add(label, c);
  }
  
  @Override
  public void update() {
    label.setText(boundLabel.getTextAsHTML());

    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    label.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    
    if(displayed) {Main.main.setSize(boundLabel.width, boundLabel.height);}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
    update();
  }

  @Override
  public Widget getBoundWidget() {
    return boundLabel;
  }
}
