package include.java.gui.popupMenu;

import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class TrayOpenButton extends MenuItem implements ActionListener {
  private JFrame boundFrame;
  private SystemTray boundTray;
  private TrayIcon boundIcon;

  public TrayOpenButton(JFrame frame, SystemTray tray, TrayIcon icon) {
    super("Open");

    boundFrame = frame;
    boundTray = tray;
    boundIcon = icon;

    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boundFrame.setVisible(true);
    boundTray.remove(boundIcon);
  }
}
