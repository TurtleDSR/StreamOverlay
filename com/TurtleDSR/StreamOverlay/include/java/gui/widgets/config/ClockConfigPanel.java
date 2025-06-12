package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Clock;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

public class ClockConfigPanel extends ConfigPanel implements ActionListener {
  private Clock boundClock;
  private ServerConfig config;

  private boolean displayed = false;

  private JCheckBox zone;
  private JCheckBox date;
  private JCheckBox time;

  private JPanel blank;

  private JButton selectButton;

  public ClockConfigPanel(Clock clock, Font font, ServerConfig config) {
    super(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.gridx = 0;
    c.gridy = 0;

    boundClock = clock;
    this.config = config;

    setBackground(ServerConfig.hextoColor(config.backgroundColor));

    selectButton = new JButton("Display " + clock.getId());
    selectButton.setActionCommand("select");
    selectButton.addActionListener(this);

    zone = new JCheckBox("Show Timezone?");
    date = new JCheckBox("Show Date?");
    time = new JCheckBox("Show Time?");

    zone.addActionListener(this);
    date.addActionListener(this);
    time.addActionListener(this);

    zone.setFont(font);
    date.setFont(font);
    time.setFont(font);

    zone.setSelected(boundClock.showZone);
    date.setSelected(boundClock.showDate);
    time.setSelected(boundClock.showTime);

    zone.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    date.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    time.setForeground(ServerConfig.hextoColor(config.foregroundColor));

    zone.setBackground(ServerConfig.hextoColor(config.backgroundColor));
    date.setBackground(ServerConfig.hextoColor(config.backgroundColor));
    time.setBackground(ServerConfig.hextoColor(config.backgroundColor));

    blank = new JPanel();
    blank.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    blank.setBackground(ServerConfig.hextoColor(config.backgroundColor));

    add(zone, c);
    c.gridy++;
    add(date, c);
    c.gridy++;
    add(time, c);
    c.gridy++;
    add(blank, c); //blank space
    c.gridy++;
    c.anchor = GridBagConstraints.CENTER;
    add(selectButton, c);
  }

  @Override
  public void update() {
    setBackground(ServerConfig.hextoColor(config.backgroundColor));
    blank.setBackground(ServerConfig.hextoColor(config.backgroundColor));

    zone.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    date.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    time.setForeground(ServerConfig.hextoColor(config.foregroundColor));

    zone.setBackground(ServerConfig.hextoColor(config.backgroundColor));
    date.setBackground(ServerConfig.hextoColor(config.backgroundColor));
    time.setBackground(ServerConfig.hextoColor(config.backgroundColor));

    selectButton.setText("Display " + boundClock.getId());

    if(displayed) {setVisible(true); Main.main.setSize(295, 255); Main.main.revalidate();} else {setVisible(false);}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
    update();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    update();
    if(e.getActionCommand() == "select") {
      Main.main.displayed = config.panelMap.get(boundClock.getId());
    } else {
      boundClock.setToggles(zone.isSelected(), date.isSelected(), time.isSelected());
    }
  }

  @Override
  public Widget getBoundWidget() {
    return boundClock;
  }
}
