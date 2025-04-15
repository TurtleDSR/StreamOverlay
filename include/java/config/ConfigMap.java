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
        String[] cur = configFileScanner.nextLine().split("=");
        configMap.put(cur[0], cur[1]);
      }

      configFileScanner.close();
      return true;

    } catch(FileNotFoundException e) { //return false if there is an FileNotFoundException
      try{FileWriter w = new FileWriter(new File(filePath)); 
        w.append(ConfigBuilder.defaultSettings());
        w.close();
      } catch(IOException ex) {
        System.err.println(ex.getMessage());
        System.exit(1);
      }
      return false;
    }
  }

  public static void rewriteConfigFiles() {
    try{
      FileWriter f = new FileWriter(new File("config/config.dat"), false);
      f.append(ConfigBuilder.defaultSettings());
      f.close();

      f = new FileWriter(new File("config/default.dat"), false);
      f.append(ConfigBuilder.defaultSettings());
      f.close();
    } catch(IOException e) {
      System.exit(1);
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
