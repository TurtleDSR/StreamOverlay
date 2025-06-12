package com.TurtleDSR.StreamOverlay.include.java.gui.popupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.TurtleDSR.StreamOverlay.Main;

public class SettingsButton extends JMenuItem implements ActionListener{
  public SettingsButton() {
    super("Open Settings");
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Main.main.openSettings();
  }
}