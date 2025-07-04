package com.TurtleDSR.StreamOverlay.include.java.gui.popupMenu;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.TurtleDSR.StreamOverlay.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton extends JMenuItem implements ActionListener {
  public ExitButton() {
    super("Exit");
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    confirmExit();
  }

  private void confirmExit() {
    Main.main.setAlwaysOnTop(false);
    int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (choice == JOptionPane.YES_OPTION) {
      System.exit(0); // Exit the application
    }
    Main.main.setAlwaysOnTop(true);
  }
}
