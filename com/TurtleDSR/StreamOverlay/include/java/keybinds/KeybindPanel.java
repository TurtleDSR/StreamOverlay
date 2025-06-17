package com.TurtleDSR.StreamOverlay.include.java.keybinds;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ConfigPanel;

public final class KeybindPanel extends JPanel implements ActionListener {
  public boolean listening;

  private Keybind boundKeybind;

  private Set<Integer> keySet = new HashSet<>();

  private JTextField bindingText;
  private JButton bindingButton;
  private JButton clearButton;

  private ConfigPanel boundPanel;

  public KeybindPanel(Keybind binding) {
    super(new GridBagLayout());

    boundKeybind = binding;
    boundKeybind.bindPanel(this);

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(0, 1, 0, 1);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 3;
    c.weighty = 1;

    bindingText = new JTextField(boundKeybind.getKeybindText());
    bindingText.setEditable(false);

    bindingButton = new JButton("Edit Keybind");
    bindingButton.setActionCommand("bind");
    bindingButton.addActionListener(this);

    clearButton = new JButton("Clear");
    clearButton.setActionCommand("clear");
    clearButton.addActionListener(this);

    add(bindingText, c);
    c.gridx++;
    c.weightx = 0.1;
    add(bindingButton, c);
    c.gridx++;
    add(clearButton, c);
    c.gridx++;
  }

  public void update() {
    bindingText.setText(boundKeybind.getKeybindText());
    if(boundPanel != null){boundPanel.update();}
  }

  public void listen(boolean press) {
    if(listening) {
      if(!press && Main.keyMasks.isEmpty() && !keySet.isEmpty()) {
        listening = false;
        keySet.clear();
      } else {
        Iterator<Integer> iter = Main.keyMasks.iterator();
        while(iter.hasNext()) {
          Integer cur = iter.next();
          if(!keySet.contains(cur)) {
            keySet.add(cur);
            boundKeybind.addKey(cur);
            update();
          }
        }
      }
    }
  }

  public void bind(ConfigPanel panel) {
    boundPanel = panel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if(cmd.equals("bind")) {
      boundKeybind.resetKeys();
      listening = true;
      bindingText.setText("Press keys");
      update();
    } else if(cmd.equals("clear")) {
      bindingText.setText("None");
      boundKeybind.resetKeys();
      update();
    }
  }
}
