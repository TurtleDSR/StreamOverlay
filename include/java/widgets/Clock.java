package include.java.widgets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clock implements Widget{
  String id;

  public Clock(String id) {
    this.id = id;
  }

  @Override
  public String getWidgetProperties() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy'\n'hh:mm:ss a"));
  }

  @Override
  public void updateConfigMap() {}

  @Override
  public void readConfigData() {}
}
