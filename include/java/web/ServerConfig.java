package include.java.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Color;

public class ServerConfig {
  private static int configEntryCount = 6;

  public static final boolean RESETCONFIGS = true;
  public static final boolean DONOTRESETCONFIGS = false;

  public int port;

  public String textColor;
  public float textOpacity;

  public String backgroundColor;
  public float backgroundOpacity;

  public int runCount;

  public ServerConfig(boolean resetConfigs) { //regenerates based on flag
    if(resetConfigs) {
      resetConfigs();
    } else {
      if(!readConfigData()) {
        resetConfigs();
      }
    }
  }

  public ServerConfig() { //does not regenerate configs
    if(!readConfigData()) {
      resetConfigs();
    }
  }

  public boolean readConfigData() { //returns false if file was not found or data was unreadable
    try {FileInputStream configFileStream = new FileInputStream(new File("config.dat"));
      byte[] configFileByteArray = configFileStream.readAllBytes(); //read file as bytes
      configFileStream.close(); //close stream

      String[] configs = new String(configFileByteArray).split("\n"); //turn file into string array of config strings

      if(configs.length != configEntryCount) { //check if there arent enough strings in config file
        return false;
      }

      port = Integer.parseInt(configs[0]);
      textColor = configs[1];
      textOpacity = Float.parseFloat(configs[2]);
      backgroundColor = configs[3];
      backgroundOpacity = Float.parseFloat(configs[4]);
      runCount = Integer.parseInt(configs[5]);

      return true;

    } catch(IOException e) { //return false if there is an FileNotFou
      return false;
    }
  }

  public boolean writeConfigs() { //returns false if anything fails
    try{FileWriter writer = new FileWriter(new File("config.dat"), false);
      writer.write(configsToString());
      writer.close();

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  public void resetConfigs() {
    port = 8080;
    textColor = "#1675fa";
    backgroundColor = "#07182cc2";

    writeConfigs();
  }

  public String configsToString() { //returns a string of all configs separated by newlines
    StringBuilder builder = new StringBuilder();

    builder.append(port + "\n"); //add all config variables to file
    builder.append(textColor + "\n");
    builder.append(textOpacity + "\n");
    builder.append(backgroundColor + "\n");
    builder.append(backgroundOpacity + "\n");
    builder.append(runCount + "");

    return builder.toString();
  }

  public static Color hextoColor(String hex) { //turns hexcode into rgb value and passes it into a new Color object
    return new Color(Integer.parseInt(hex.substring(1, hex.length()), 16));
  }
}
