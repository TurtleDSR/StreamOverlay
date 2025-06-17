package com.TurtleDSR.StreamOverlay.include.java.config.converter;

import java.util.regex.Pattern;

import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

public class KeybindConverter extends TypeConverter {
  @Override
  public Object convertType(String input) {
    Keybind out = new Keybind();
    if(input != "") {
      String[] keys = input.split(Pattern.quote("+"));

      for (String key : keys) {
        out.addKey(Integer.parseInt(key));
      }
    }
    return out;
  }

  public static String convertOutput(Keybind in) {
    String out = "";
    for (int key : in.getKeyList()) {
      if(out.equals("")) {
        out = key + "";
      } else {
        out += "+" + key;
      }
    }
    return out;
  }
}
