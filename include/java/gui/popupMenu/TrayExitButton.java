package include.java.gui.popupMenu;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayExitButton extends MenuItem implements ActionListener {
  public TrayExitButton() {
    super("Exit");
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}
