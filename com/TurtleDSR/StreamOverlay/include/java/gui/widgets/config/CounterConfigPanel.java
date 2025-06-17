package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.settings.DefaultTextField;
import com.TurtleDSR.StreamOverlay.include.java.keybinds.KeybindPanel;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Counter;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

public class CounterConfigPanel extends ConfigPanel implements ActionListener{
  private  Counter boundCounter;
  private ServerConfig config;

  private JPanel rename;
  private DefaultTextField renameField;
  private JButton renameButton;

  private JSpinner countInput;

  private JTextField labelField;

  private JButton displayButton;

  private JPanel container;

  private KeybindPanel incrementPanel;
  private KeybindPanel decrementPanel;

  private boolean displayed;

  public CounterConfigPanel(Counter counter, ServerConfig config) {
    super(new GridBagLayout());

    container = new JPanel(new GridBagLayout());

    boundCounter = counter;
    this.config = config;

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 1;
    c.gridx = 0;
    c.gridy = 0;

    rename = new JPanel(new GridBagLayout());
    renameField = new DefaultTextField(boundCounter.getId());
    rename.add(renameField, c);

    c.gridx++;
    c.weightx = 0.01;

    renameButton = new JButton("Rename");
    renameButton.addActionListener(this);
    renameButton.setActionCommand("rename");
    rename.add(renameButton, c);

    countInput = new JSpinner(new SpinnerNumberModel(boundCounter.count, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
    countInput.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        boundCounter.count = (Integer) countInput.getValue();
        boundCounter.update();
      }
    });

    JPanel countPanel = new JPanel(new GridBagLayout());

    c.gridx = 0;

    countPanel.add(new JLabel("Count: "), c);

    c.gridx++;
    c.weightx = 1;

    countPanel.add(countInput, c);

    c.weightx = 0.01;
    c.gridx = 0;

    JPanel label = new JPanel(new GridBagLayout());

    labelField = new JTextField(boundCounter.label);
    labelField.setActionCommand("label");
    labelField.addActionListener(this);

    label.add(new JLabel("Label: "), c);

    c.gridx++;
    c.weightx = 1;

    label.add(labelField, c);

    JPanel increment = new JPanel(new GridBagLayout());
    c.gridx = 0;
    c.weightx = 1;

    increment.add(new JLabel("Increment: "), c);

    c.gridx = 1;
    c.weightx = 1;
    
    incrementPanel = new KeybindPanel(boundCounter.increment);
    incrementPanel.bind(this);
    increment.add(incrementPanel, c);

    JPanel decrement = new JPanel(new GridBagLayout());
    c.gridx = 0;
    c.weightx = 1;

    decrement.add(new JLabel("Decrement: "), c);

    c.gridx = 1;
    c.weightx = 1;
    
    decrementPanel = new KeybindPanel(boundCounter.decrement);
    decrementPanel.bind(this);
    decrement.add(decrementPanel, c);

    displayButton = new JButton("Display " + boundCounter.getId());
    displayButton.setActionCommand("select");
    displayButton.addActionListener(this);

    c.anchor = GridBagConstraints.NORTHWEST;
    c.insets = new Insets(0, 0, 3, 0);
    c.weightx = 1;
    c.weighty = 1;
    c.gridx = 0;
    c.gridy = 0;

    container.add(rename, c);
    c.gridy++;
    container.add(countPanel, c);
    c.gridy++;
    container.add(label, c);
    c.gridy++;
    container.add(increment, c);
    c.gridy++;
    container.add(decrement, c);
    c.gridy++;
    container.add(displayButton, c);
    
    c.insets = new Insets(0, 5, 0, 5);
    add(container, c);
  }

  @Override
  public void update() {
    displayButton.setText("Display " + boundCounter.getId());
    countInput.setValue(boundCounter.count);

    if(displayed) {Main.main.pack();}
  }

  @Override
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
  }

  @Override
  public Widget getBoundWidget() {
    return boundCounter;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("select")) {
      Main.main.displayed = config.panelMap.get(boundCounter.getId());
    } else if(e.getActionCommand().equals("rename")) {
      if(!renameField.getText().matches(".*\\s.*") && !config.widgetMap.containsKey(renameField.getText())) {
        config.renameWidget(boundCounter.getId(), renameField.getText());
      }
    } else if(e.getActionCommand().equals("label")) {
      boundCounter.label = labelField.getText();
      boundCounter.update();
    }
  }
}
