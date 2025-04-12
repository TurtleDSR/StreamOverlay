package include.java.config;

public class ConfigBuilder { //used to easily write a configs to the file
  private StringBuilder builder;

  public ConfigBuilder() {
    builder = new StringBuilder();
  }

  public ConfigBuilder(String str) {
    builder = new StringBuilder(str);
  }

  public void appendConfig(String configName, String value) {
    builder.append(configName + ":" + value + "\n");
  }

  public StringBuilder getBuilder() {
    return builder;
  }

  public String toString() {
    return builder.toString().strip();
  }
}
