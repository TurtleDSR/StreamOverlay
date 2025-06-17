package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public final class BooleanConverter extends TypeConverter { //converts string to Integer
  @Override
  public Object convertType(String input) {
    if(input.equalsIgnoreCase("true")) {
      return true;
    } else {
      return false;
    }
  }
}