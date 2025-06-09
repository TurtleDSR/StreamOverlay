package include.java.config.converter;

public final class FloatConverter implements TypeConverter{ //converts string to float
  @Override
  public Object convertType(String input) {
    return Float.parseFloat(input);
  }
}