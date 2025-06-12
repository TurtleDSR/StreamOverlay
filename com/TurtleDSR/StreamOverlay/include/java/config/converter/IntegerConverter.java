package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public final class IntegerConverter implements TypeConverter { //converts string to Integer
  @Override
  public Object convertType(String input) {
    return Integer.parseInt(input);
  }
}