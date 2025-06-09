package include.java.gui.popupMenu;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TrayButton extends JMenuItem implements ActionListener {
  private SystemTray tray;
  private JFrame frame;

  public TrayButton(SystemTray tray, JFrame frame) {
    super("Hide to tray");
    this.frame = frame;
    this.tray = tray;
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Image image = null;
    PopupMenu menu = new PopupMenu();

    try{image = ImageIO.read(getClass().getResource("/assets/img/pikin.jpg"));} catch(IOException ex) {System.out.println(1);}
    TrayIcon icon = new TrayIcon(image, "Stream Overlay", menu);

    icon.setImageAutoSize(true);

    menu.add(new TrayOpenButton(frame, tray, icon));
    menu.add(new TrayExitButton());

    try{tray.add(icon);
    frame.setVisible(false);
    } catch (AWTException ex) {System.out.println(1);}
  }
}