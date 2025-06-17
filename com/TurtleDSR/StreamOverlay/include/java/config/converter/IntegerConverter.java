package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public final class IntegerConverter extends TypeConverter { //converts string to Integer
  @Override
  public Object convertType(String input) {
    return Integer.parseInt(input);
  }
}