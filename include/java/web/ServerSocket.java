package include.java.web;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;   

public class ServerSocket {
  public HttpServer server;

  public ServerConfig config;

  public ServerSocket() throws IOException{ //generates the server and maps handlers to paths
    this.config = new ServerConfig(ServerConfig.RESETCONFIGS); //generate default config

    jsHandler jsHandler = new jsHandler();
    jsHandler.parent = this;

    server = HttpServer.create(new InetSocketAddress(config.port), 0);
    server.createContext("/", new IndexHandler());
    server.createContext("/static", new StaticHandler());
    server.createContext("/dat", jsHandler);

    server.start();
  }

  public ServerSocket(ServerConfig config) throws IOException{
    this.config = config; //create config with given info

    server = HttpServer.create(new InetSocketAddress(config.port), 0);
    server.createContext("/", new IndexHandler());
    server.createContext("/static", new StaticHandler());
    server.createContext("/dat", new jsHandler());

    server.start();
  }

  public String getDataResponse() {
    StringBuilder builder = new StringBuilder();

    builder.append(config.textColor + "\n");
    builder.append(config.backgroundColor + "\n");

    return builder.toString();
  }

  static class IndexHandler implements HttpHandler { //sends index.html file
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      OutputStream responseStream = exchange.getResponseBody();
      String htmlString = new String(Files.readAllBytes(Paths.get("include/web/index.html")));

      exchange.getResponseHeaders().set("Content-Type", "text/html");
      exchange.sendResponseHeaders(200, htmlString.length());
      responseStream.write(htmlString.getBytes());
      responseStream.close();
    }
  }

  static class StaticHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException { //sends css and js files from static folder
      OutputStream responseStream = exchange.getResponseBody();
      String responseString = new String(Files.readAllBytes(Paths.get("include/web/" + exchange.getRequestURI().getPath())));

      exchange.sendResponseHeaders(200, responseString.length());
      responseStream.write(responseString.getBytes());
      responseStream.close();
    }
  }

  static class jsHandler implements HttpHandler {
    public ServerSocket parent;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
      String requestMethod = exchange.getRequestMethod();
      String requestURI = exchange.getRequestURI().toString();

      String responseString = parent.getDataResponse(); //response of all variables separated by newlines
      OutputStream responseStream = exchange.getResponseBody(); 
      
      if(requestURI.equals("/dat/get.data")) {
        if(requestMethod.equalsIgnoreCase("POST")) { //check if client sent a POST request
          exchange.sendResponseHeaders(200, responseString.length()); //send response
          responseStream.write(responseString.getBytes());
          responseStream.close();
        }
      }
    }
  }
}

