package com.TurtleDSR.StreamOverlay.include.java.gui.settings.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.TurtleDSR.StreamOverlay.Main;

public class SystemPanel extends JPanel implements ActionListener{
  private JButton displayButton;

  public SystemPanel() {
    super(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.NORTHWEST;
    c.weightx = 1;
    c.weighty = 1;
    c.insets = new Insets(0, 3, 0, 3);

    displayButton = new JButton("Exit Settings");
    displayButton.setActionCommand("display");
    displayButton.addActionListener(this);

    add(displayButton, c);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getActionCommand().equals("display")) {
      Main.main.closeSettings();
    } else {}
  }
}
