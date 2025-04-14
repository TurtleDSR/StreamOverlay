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

    server = HttpServer.create(new InetSocketAddress(config.port), 0);
    server.createContext("/", new IndexHandler());
    server.createContext("/static", new StaticHandler());
    server.createContext("/dat", jsHandler);

    server.start();
  }

  public ServerSocket(ServerConfig config) {
    this.config = config; //create config with given info

    jsHandler jsHandler = new jsHandler();
    jsHandler.parent = this;

    try{server = HttpServer.create(new InetSocketAddress(config.port), 0);
      server.createContext("/", new IndexHandler());
      server.createContext("/static", new StaticHandler());
      server.createContext("/dat", jsHandler);
    } catch(IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    server.start();
  }

  public String getStyleResponse() { //generates a response string for js data request using StringBuilder
    StringBuilder builder = new StringBuilder();

    builder.append(config.textColor + "\n");
    builder.append(config.textOpacity + "\n");
    builder.append(config.backgroundColor + "\n");
    builder.append(config.backgroundOpacity + "\n");

    return builder.toString();
  }

  public String getCounterResponse(int counterId) {
    return config.runCount + "";
  }

  static class IndexHandler implements HttpHandler { //sends index.html file
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      OutputStream responseStream = exchange.getResponseBody();
      byte[] html = Files.readAllBytes(Paths.get("include/web/index.html"));

      exchange.sendResponseHeaders(200, html.length);
      responseStream.write(html);
      responseStream.close();
    }
  }

  static class StaticHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException { //sends css and js files from static folder
      OutputStream responseStream = exchange.getResponseBody();
      byte[] response = Files.readAllBytes(Paths.get("include/web" + exchange.getRequestURI().getPath()));

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
      
      if(requestURI.equals("/dat/get.style")) {
        if(requestMethod.equalsIgnoreCase("POST")) { //check if client sent a POST request
          response = parent.getStyleResponse().getBytes(); //response of all variables separated by newlines

          exchange.sendResponseHeaders(200, response.length); //send response
          responseStream.write(response);
          responseStream.close();
        }
      }

      if(requestURI.equals("/dat/get.counter")) {
        if(requestMethod.equalsIgnoreCase("POST")) { //check if client sent a POST request
          response = parent.getCounterResponse(Integer.parseInt(new String(exchange.getRequestBody().readAllBytes()))).getBytes();

          exchange.sendResponseHeaders(200, response.length); //send response
          responseStream.write(response);
          responseStream.close();
        }
      }
    }
  }
}

