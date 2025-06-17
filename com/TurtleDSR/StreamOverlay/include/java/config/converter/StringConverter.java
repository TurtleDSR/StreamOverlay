package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public class StringConverter extends TypeConverter {
  @Override
  public Object convertType(String input) {
    String out = "";
    char[] arr = input.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if(arr[i] == '\\') {
        i++;
        if(arr[i] == 'n') {
          out += '\n';
        } else if(arr[i] == '\\') {
          out += '\\';
        } else if(arr[i] == 's') {
          out += ' ';
        }
      } else {
        out += arr[i];
      }
    }
    return out;
  }

  public static String convertOutput(String input) {
    String out = "";
    char[] arr = input.toCharArray();
    for (int i = 0; i < arr.length; i++) {
      if(arr[i] == '\n') {
        out += "\\n";
      } else if(arr[i] == '\\') {
        out += "\\\\";
      } else if(arr[i] == ' ') {
        out += "\\s";
      } else {
        out += arr[i];
      }
    }
    return out;
  }
}
