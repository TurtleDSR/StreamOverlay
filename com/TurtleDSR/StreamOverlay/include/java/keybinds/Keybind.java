package com.TurtleDSR.StreamOverlay.include.java.keybinds;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.widgets.Widget;

import java.util.LinkedList;

public class Keybind {

  private boolean update = false;
  private LinkedList<Integer> keyPtrs = new LinkedList<Integer>();

  public Widget parent;

  public void checkBind() {
    for(int i : keyPtrs) {
      if(!Main.keyMasks[i]) {
        return;
      }
    }
    update = true;
    parent.update();
  }

  public void addKey(int keyCode) {
    keyPtrs.add(keyCode);
  }

  public void resetKeys() {
    keyPtrs = new LinkedList<Integer>();
  }

  public boolean update() {
    if(update) {
      update = false;
      return true;
    }
    return false;
  }
}
