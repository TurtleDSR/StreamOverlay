package com.TurtleDSR.StreamOverlay.include.java.keybinds;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.util.LinkedList;

public class Keybind {
  private boolean update = false;
  private LinkedList<Integer> keyPtrs = new LinkedList<Integer>();
  private String keyText;

  private KeybindPanel boundPanel;

  public Widget parent;

  public Keybind() {
    Main.keybinds.add(this);
  }

  public void checkBind(boolean press) {
    if(boundPanel != null) {
      boundPanel.listen(press);
    }

    if(press && keyPtrs.size() != 0 && !boundPanel.listening) {
      for(int i : keyPtrs) {
        if(!Main.keyMasks.contains(i)) {
          return;
        }
      }
      update = true;
      parent.update();
    }
  }

  public void addKey(int keyCode) {
    if(keyPtrs.size() == 0) {
      keyText = NativeKeyEvent.getKeyText(keyCode);
    } else {
      keyText += " + " + NativeKeyEvent.getKeyText(keyCode);
    }
    if(boundPanel != null) {boundPanel.update();}
    keyPtrs.add(keyCode);
  }

  public void resetKeys() {
    keyText = "None";
    keyPtrs = new LinkedList<Integer>();
  }

  public boolean update() {
    if(update) {
      update = false;
      return true;
    }
    return false;
  }

  public String getKeybindText() {
    return keyText;
  }

  public void bindPanel(KeybindPanel panel) {
    boundPanel = panel;
  }

  public LinkedList<Integer> getKeyList() {
    return keyPtrs;
  }
}
