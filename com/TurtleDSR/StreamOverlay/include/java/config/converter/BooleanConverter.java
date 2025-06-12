package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public final class BooleanConverter implements TypeConverter { //converts string to Integer
  @Override
  public Object convertType(String input) {
    if(input.equalsIgnoreCase("true")) {
      return true;
    } else {
      return false;
    }
  }
}