package include.java.config;

import java.util.*;

import include.java.config.converter.TypeConverter;

import java.io.*;

public final class ConfigMap {
  private Map<String, Map<String, String>> configMap;

  public ConfigMap(String filePath) {
    configMap = new HashMap<String, Map<String, String>>();
    readFile(filePath);
  }

  public ConfigMap() {
    configMap = new HashMap<String, Map<String, String>>();
  }

  public void writeConfigsToFile(String filePath) {
    try {FileWriter fw = new FileWriter(filePath);
      
      Set<String> objs = configMap.keySet();
      for (String obj : objs) {
        fw.append(obj);
        for (String key : configMap.get(obj).keySet()) {
          fw.append(key + "=" + configMap.get(obj).get(key));
        }
        fw.append("/end\n");
      }
      fw.close();

    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  public boolean readFile(String filePath) { //returns false if it is unable to read the file
    boolean inObject = false;

    try {Scanner configFileScanner = new Scanner(new File(filePath));
      configFileScanner.useDelimiter("\\R+");

      String curLine = "";
      String objName = "";
      while (configFileScanner.hasNext()) {
        if(inObject) {
          curLine = configFileScanner.next().strip();
          if(!curLine.equals("")) {
            if(!curLine.equals("/end")) {
              String[] cur = curLine.split("=");
              if(cur.length == 2) {
                configMap.get(objName).put(cur[0], cur[1]);
              } else {
                configMap.get(objName).put(cur[0], "");
              }
            } else {
              inObject = false;
            }
          }
        } else {
            inObject = true;
            objName = configFileScanner.next();
            configMap.put(objName, new HashMap<String, String>());
        }
      }

      configFileScanner.close();
      return true;

    } catch(FileNotFoundException e) { //return false if there is an FileNotFoundException
      try{FileWriter w = new FileWriter(new File(filePath)); 
        w.append(defaultSettings());
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
      f.append(defaultSettings());
      f.close();

      f = new FileWriter(new File("config/default.dat"), false);
      f.append(defaultSettings());
      f.close();
    } catch(IOException e) {
      System.exit(1);
    }
  }

  public Object get(String objectName, String key, TypeConverter converter) {
    return converter.convertType(configMap.get(objectName).get(key));
  }

  public String get(String objectName, String key) {
    return configMap.get(objectName).get(key);
  }

  public void set(String objectName, String key, String value) {
    configMap.get(objectName).replace(key, value);
  }

  public void put(String objectName, String key, String value) {
    configMap.get(objectName).put(key, value);
  }

  public Set<String> getObjectKeys() {
    return configMap.keySet();
  }

  public static String defaultSettings() {
    StringBuilder b = new StringBuilder();
    
    b.append("server\n")
    .append("port=8080")
    .append("foregroundColor=#1675fa\n")
    .append("foregroundAlpha=1.0\n")
    .append("backgroundColor=#07182c\n")
    .append("backgroundAlpha=0.8\n")
    .append("/end\n")
    .append("\n")
    .append("counter1\n")
    .append("count=0\n")
    .append("prenum=Runs:\n")
    .append("/end\n");

    return b.toString().strip();
  }
}
