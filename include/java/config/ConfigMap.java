package include.java.config;

import java.util.*;
import java.io.*;

public final class ConfigMap {
  private Map<String, String> configMap;

  public ConfigMap(String filePath) {
    configMap = new HashMap<String, String>();
    readFile(filePath);
  }

  public ConfigMap() {
    configMap = new HashMap<String, String>();
  }

  public boolean readFile(String filePath) { //returns false if it is unable to read the file
    try {Scanner configFileScanner = new Scanner(new File(filePath));
      while (configFileScanner.hasNext()) {
        String[] cur = configFileScanner.nextLine().split(":");
        configMap.put(cur[0], cur[1]);
      }

      configFileScanner.close();
      return true;

    } catch(IOException e) { //return false if there is an FileNotFoundException
      return false;
    }
  }

  public Object getValue(String key, TypeConverter converter) {
    return converter.convertType(configMap.get(key));
  }

  public String getValue(String key) {
    return configMap.get(key);
  }

  public void setValue(String key, String value) {
    configMap.replace(key, value);
  }

  public void putValue(String key, String value) {
    configMap.put(key, value);
  }
}
