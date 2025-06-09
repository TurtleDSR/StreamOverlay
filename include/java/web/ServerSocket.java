package include.java.web;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;

import include.java.config.*;

public final class ServerSocket {
  public HttpServer server;

  public ServerConfig config;

  public ServerSocket() throws IOException{ //generates the server and maps handlers to paths
    this.config = new ServerConfig(ServerConfig.RESETCONFIGS); //generate default config

    jsHandler jsHandler = new jsHandler();
    jsHandler.parent = this;

    MainHandler mainHandler = new MainHandler();

    server = HttpServer.create(new InetSocketAddress(config.port), 0);
    server.createContext("/", mainHandler);
    server.createContext("/static", mainHandler);
    server.createContext("/dat", jsHandler);

    server.start();
  }

  public ServerSocket(ServerConfig config) {
    this.config = config; //create config with given info

    jsHandler jsHandler = new jsHandler();
    jsHandler.parent = this;

    MainHandler mainHandler = new MainHandler();

    try{server = HttpServer.create(new InetSocketAddress(config.port), 0);
      server.createContext("/", mainHandler);
      server.createContext("/static", mainHandler);
      server.createContext("/get", jsHandler);
    } catch(IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    server.start();
  }

  public String getStyleResponse() { //generates a response string for js data request using StringBuilder
    StringBuilder builder = new StringBuilder();

    builder.append(config.foregroundColor + "\n");
    builder.append(config.foregroundAlpha + "\n");
    builder.append(config.backgroundColor + "\n");
    builder.append(config.backgroundAlpha + "\n");

    return builder.toString();
  }

  public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
    byte[] combined = new byte[a.length + b.length];

    System.arraycopy(a,0,combined,0 ,a.length);
    System.arraycopy(b,0,combined,a.length,b.length);

    return combined;
  }

  static class MainHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException { //sends css and js files from static folder
      OutputStream responseStream = exchange.getResponseBody();
      byte[] response = new byte[0];

      String[] filePathArr = exchange.getRequestURI().toString().split("/");
      String[] fileNameArr = filePathArr[filePathArr.length - 1].split("\\.");

      if(filePathArr.length == 2) { //root path
        String requestFilePath = exchange.getRequestURI().getPath();
        String fileName = requestFilePath.split(":")[0];

        response = Files.readAllBytes(Paths.get("include/web" + fileName + ".html"));

      } else if(fileNameArr[1].equals("js")) {
        if(!fileNameArr[0].equals("main")) {
          String[] refererId = exchange.getRequestHeaders().getFirst("Referer").split("/");
          refererId = refererId[refererId.length - 1].split("\\.");
          refererId = refererId[0].split(":");
          String id = refererId[1];

          String oDefString = "";

          oDefString = "\no = new " + refererId[0] + "(\"" + id + "\");"; //defines an object for the client
          
          response = concatenateByteArrays(Files.readAllBytes(Paths.get("include/web" + exchange.getRequestURI().getPath())), oDefString.getBytes());
        } else {
          response = Files.readAllBytes(Paths.get("include/web" + exchange.getRequestURI().getPath()));
        }  
      } else {
        response = Files.readAllBytes(Paths.get("include/web" + exchange.getRequestURI().getPath()));
      }

      exchange.sendResponseHeaders(200, response.length);
      responseStream.write(response);
      responseStream.close();
    }
  }

  static class jsHandler implements HttpHandler {
    public ServerSocket parent; //stores parent ServerSocket for requesting configs

    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String requestMethod = exchange.getRequestMethod();
      String requestURI = exchange.getRequestURI().toString();

      byte[] response;

      OutputStream responseStream = exchange.getResponseBody(); 
      
      if(requestURI.equals("/get/style")) {
        if(requestMethod.equalsIgnoreCase("POST")) { //check if client sent a POST request
          response = parent.getStyleResponse().getBytes(); //response of all variables separated by newlines

          exchange.sendResponseHeaders(200, response.length); //send response
          responseStream.write(response);
          responseStream.close();
        }
      }

      if(requestURI.equals("/get/widget")) {
        if(requestMethod.equalsIgnoreCase("POST")) { //check if client sent a POST request
          response = parent.config.widgetMap.get(new String(exchange.getRequestBody().readAllBytes())).getWidgetProperties().getBytes();

          exchange.sendResponseHeaders(200, response.length); //send response
          responseStream.write(response);
          responseStream.close();
        }
      }
    }
  }
}

