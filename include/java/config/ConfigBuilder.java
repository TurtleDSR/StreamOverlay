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
    builder.append(configName + "=" + value + "\n");
  }

  public StringBuilder getBuilder() {
    return builder;
  }

  public String toString() {
    return builder.toString().strip();
  }

  public static String defaultSettings() {
    StringBuilder b = new StringBuilder();
    b.append("port=8080\n")
    .append("runCount=0\n")
    .append("textColor=#1675fa\n")
    .append("textOpacity=1.0\n")
    .append("backgroundColor=#07182c\n")
    .append("backgroundOpacity=0.8\n");

    return b.toString().strip();
  }
}
