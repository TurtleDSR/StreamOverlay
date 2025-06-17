package com.TurtleDSR.StreamOverlay.include.java.config.converter;

public final class FloatConverter extends TypeConverter{ //converts string to float
  @Override
  public Object convertType(String input) {
    return Float.parseFloat(input);
  }
}