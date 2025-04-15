package include.java.config.converter;

import include.java.config.TypeConverter;

public final class FloatConverter implements TypeConverter{ //converts string to float
  @Override
  public Object convertType(String input) {
    return Float.parseFloat(input);
  }
}