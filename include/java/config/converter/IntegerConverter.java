package include.java.config.converter;

import include.java.config.TypeConverter;

public final class IntegerConverter implements TypeConverter { //converts string to Integer
  @Override
  public Object convertType(String input) {
    return Integer.parseInt(input);
  }
}