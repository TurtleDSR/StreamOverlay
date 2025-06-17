package com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config;

import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.settings.DefaultTextField;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Label;

public class LabelConfigPanel extends ConfigPanel implements ActionListener{
  private Label boundLabel;

  private boolean displayed;
  private ServerConfig config;

  private JPanel rename;
  private DefaultTextField renameField;
  private JButton renameButton;

  private JTextArea textField;
  private JButton textButton;

  private JButton displayButton;

  private JSpinner widthInput;
  private JSpinner heightInput;

  public LabelConfigPanel(Label label, ServerConfig config) {
    super(new GridBagLayout());

    boundLabel = label;
    this.config = config;

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 1;
    c.gridx = 0;
    c.gridy = 0;

    rename = new JPanel(new GridBagLayout());
    renameField = new DefaultTextField(boundLabel.getId());
    rename.add(renameField, c);

    c.gridx++;
    c.weightx = 0.1;

    renameButton = new JButton("Rename");
    renameButton.addActionListener(this);
    renameButton.setActionCommand("rename");
    rename.add(renameButton, c);

    textField = new JTextArea(5 ,10);
    textField.setText(boundLabel.text);

    textButton = new JButton("Change Text");
    textButton.addActionListener(this);
    textButton.setActionCommand("text");

    widthInput = new JSpinner(new SpinnerNumberModel(boundLabel.width, 1, Integer.MAX_VALUE, 1));
    widthInput.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        boundLabel.width = (Integer) widthInput.getValue();
        boundLabel.update();
      }
    });

    JPanel widthPanel = new JPanel(new GridBagLayout());

    c.weightx = 0.1;
    c.gridx = 0;

    widthPanel.add(new JLabel("Width: "), c);

    c.weightx = 1;
    c.gridx = 1;

    widthPanel.add(widthInput, c);

    heightInput = new JSpinner(new SpinnerNumberModel(boundLabel.height, 1, Integer.MAX_VALUE, 1));
    heightInput.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        boundLabel.height = (Integer) heightInput.getValue();
        boundLabel.update();
      }
    });

    JPanel heightPanel = new JPanel(new GridBagLayout());

    c.weightx = 0.1;
    c.gridx = 0;

    heightPanel.add(new JLabel("Height: "), c);

    c.weightx = 1;
    c.gridx = 1;

    heightPanel.add(heightInput, c);

    displayButton = new JButton("Display " + boundLabel.getId());
    displayButton.setActionCommand("select");
    displayButton.addActionListener(this);

    c.weightx = 1;
    c.insets = new Insets(0, 5, 5, 5);
    c.anchor = GridBagConstraints.NORTHWEST;
    c.gridx = 0;

    add(rename, c);
    c.gridy++;
    add(textField, c);
    c.gridy++;
    add(textButton, c);
    c.gridy++;
    add(widthPanel, c);
    c.gridy++;
    add(heightPanel, c);
    c.gridy++;
    add(displayButton, c);
  }

  @Override
  public void update() {
    displayButton.setText("Display " + boundLabel.getId());

    if(displayed) {Main.main.pack();}
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

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("select")) {
      Main.main.displayed = config.panelMap.get(boundLabel.getId());
    } else if(e.getActionCommand().equals("rename")) {
      if(!renameField.getText().matches(".*\\s.*") && !config.widgetMap.containsKey(renameField.getText())) {
        config.renameWidget(boundLabel.getId(), renameField.getText());
      }
    } else if(e.getActionCommand().equals("text")) {
      boundLabel.text = textField.getText();
      boundLabel.update();
    }
  }
}
