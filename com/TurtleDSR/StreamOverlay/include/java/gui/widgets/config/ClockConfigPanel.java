package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.settings.DefaultTextField;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Clock;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

public class ClockConfigPanel extends ConfigPanel implements ActionListener {
  private Clock boundClock;
  private ServerConfig config;

  private boolean displayed = false;

  private JPanel rename;
  private DefaultTextField renameField;
  private JButton renameButton;

  private JCheckBox zone;
  private JCheckBox date;
  private JCheckBox time;

  private JButton selectButton;

  private JPanel container;

  public ClockConfigPanel(Clock clock, ServerConfig config) {
    super(new GridBagLayout());

    container = new JPanel(new GridBagLayout());

    boundClock = clock;
    this.config = config;

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 1;

    rename = new JPanel(new GridBagLayout());
    renameField = new DefaultTextField(boundClock.getId());
    rename.add(renameField, c);

    c.gridx++;
    c.weightx = 0.1;

    renameButton = new JButton("Rename");
    renameButton.addActionListener(this);
    renameButton.setActionCommand("rename");
    rename.add(renameButton, c);

    selectButton = new JButton("Display " + clock.getId());
    selectButton.setActionCommand("select");
    selectButton.addActionListener(this);

    zone = new JCheckBox("Show Timezone?");
    date = new JCheckBox("Show Date?");
    time = new JCheckBox("Show Time?");

    zone.addActionListener(this);
    date.addActionListener(this);
    time.addActionListener(this);

    zone.setSelected(boundClock.showZone);
    date.setSelected(boundClock.showDate);
    time.setSelected(boundClock.showTime);

    c.weightx = 1;
    c.gridx = 0;
    c.anchor = GridBagConstraints.NORTHWEST;
    c.insets = new Insets(0, 0, 3, 0);

    container.add(rename, c);
    c.gridy++;
    container.add(zone, c);
    c.gridy++;
    container.add(date, c);
    c.gridy++;
    container.add(time, c);
    c.gridy++;
    container.add(selectButton, c);

    c.insets = new Insets(0, 5, 0, 5);
    add(container, c);
  }

  @Override
  public void update() {
    selectButton.setText("Display " + boundClock.getId());

    if(displayed) {Main.main.pack();}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
    update();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    update();
    if(e.getActionCommand().equals("select")) {
      Main.main.displayed = config.panelMap.get(boundClock.getId());
    } else if(e.getActionCommand().equals("rename")) {
      if(!renameField.getText().matches(".*\\s.*") && !config.widgetMap.containsKey(renameField.getText())) {
        config.renameWidget(boundClock.getId(), renameField.getText());
      }
    } else {
      if(!(zone.isSelected() || date.isSelected() || time.isSelected())) {time.setSelected(true);}
      boundClock.setToggles(zone.isSelected(), date.isSelected(), time.isSelected());
    }
  }

  @Override
  public Widget getBoundWidget() {
    return boundClock;
  }
}
