package com.labs.lab5;


import com.labs.lab4.DBService;
import com.labs.lab4.Product;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.spec.SecretKeySpec;

public class Server {

    private DBService dbService;
    private HttpServer httpServer;
    private static int JWT_EXPIRATION_MILLIS = 100;
    private int port;
    private static final byte[] JWT_SECRET = "jwt-secret-for-client-server-app-dev".getBytes(StandardCharsets.UTF_8);
    private ObjectMapper objectMapper = new ObjectMapper();


    Server(int port, DBService dbs) throws IOException {
        this.dbService = dbs;
        this.port = port;
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        this.configureEndpoints();
        this.httpServer.start();
    }


    private void configureEndpoints() {
        httpServer.createContext("/", this::handleRoot);
        httpServer.createContext("/login", this::handleLogin);
        httpServer.createContext("/add", this::handleAddUser);
        httpServer.createContext("/api/good", this::handleGood).setAuthenticator(new Authorizer(dbService, JWT_SECRET));
        httpServer.createContext( "/api/good/", this::handleSpecificGood).setAuthenticator(new Authorizer(dbService, JWT_SECRET));
    }

    private void handleRoot(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, 0);
        }
        exchange.close();
    }


    private void handleGood(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getPrincipal());
        if (exchange.getRequestMethod().equals("PUT")) {

            Product product = objectMapper.readValue(exchange.getRequestBody(), Product.class);
            if( product.validate()) {
                int gotId = dbService.createProduct(product.getTitle(),
                                                    product.getAmount(),
                                                    product.getPrice(),
                                                    product.getCategory());
                byte[] response = objectMapper.writeValueAsBytes(gotId);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(201, response.length);
                exchange.getResponseBody().write(response);
            }
            else {exchange.sendResponseHeaders(409, 0); }
        }
        else {
            exchange.getResponseBody().write("Can not add to db".getBytes(StandardCharsets.UTF_8));
            exchange.sendResponseHeaders(403, 0);
        }
        exchange.close();
    };


    private void handleLogin(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            User user = objectMapper.readValue(exchange.getRequestBody(), User.class);
            User dbServiceUser = dbService.getUser(user.getLogin());
            try {
                if (dbServiceUser != null) {
                    if (dbServiceUser.getPassword().equals(user.getPassword())) {
                        exchange.getResponseHeaders().set("Authorization", generateJWTToken(dbServiceUser.getLogin()));
                        exchange.sendResponseHeaders(200, 0);
                    } else { exchange.sendResponseHeaders(401, 0); }
                } else { exchange.sendResponseHeaders(401, 0); }
            } catch (Exception e) { e.printStackTrace(); }
        } else { exchange.sendResponseHeaders(405, 0); }
        exchange.close();
    }


    private void handleSpecificGood(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getPrincipal());
        if (exchange.getRequestMethod().equals("GET")) {
            String url = exchange.getRequestURI().toString();
            String[] param = url.split("/");
            Integer id = Integer.parseInt(param[param.length - 1]);
            System.out.println(exchange.getPrincipal());
            try {
                Product dbServiceById = dbService.getById(id);
                if (dbServiceById != null) {
                    byte[] response = dbServiceById.toString().getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, response.length);
                    exchange.getResponseBody().write(response);
                } else { exchange.sendResponseHeaders(404, 0); }
            } catch (Exception e) { e.printStackTrace(); }
        }


        else if (exchange.getRequestMethod().equals("POST")) {
            String url = exchange.getRequestURI().toString();
            String[] param = url.split("/");
            Integer id = Integer.parseInt(param[param.length - 1]);
            try {
                Product dbServiceById = dbService.getById(id);
                Product product = objectMapper.readValue(exchange.getRequestBody(), Product.class);
                if (dbServiceById != null && product != null ) {
                    if (product.validate()) {
                        dbService.updateProduct(product);
                        Product val = dbService.getById(dbServiceById.getId());
                        byte[] response = val.toString().getBytes(StandardCharsets.UTF_8);
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(201, response.length);
                        exchange.getResponseBody().write(response);
                    } else { exchange.sendResponseHeaders(204, 0); }
                } else { exchange.sendResponseHeaders(405, 0); }
            } catch (Exception e) { e.printStackTrace(); }
        }


        else if (exchange.getRequestMethod().equals("DELETE")) {
            byte[] response = "{\"Status\": \"Deleted product\"}".getBytes(StandardCharsets.UTF_8);
            String url = exchange.getRequestURI().toString();
            String[] param = url.split("/");
            Integer id = Integer.parseInt(param[param.length - 1]);
            try {
                Product dbServiceById = dbService.getById(id);
                if (dbServiceById != null) {
                    dbService.deleteProduct(dbServiceById.getId());;
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, response.length);
                    exchange.getResponseBody().write(response);
                } else { exchange.sendResponseHeaders(404, 0); }
            } catch (Exception e) { e.printStackTrace(); }
        }  else { exchange.sendResponseHeaders(405, 0); }

        exchange.close();
    }


    private static String generateJWTToken(String userLogin) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date();
        Key signingKey = new SecretKeySpec(JWT_SECRET, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(userLogin)
                .setExpiration(new Date(now.getTime()+ TimeUnit.HOURS.toMillis(JWT_EXPIRATION_MILLIS)))
                .signWith(signingKey,signatureAlgorithm);
        return builder.compact();
    }


    private void handleAddUser(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            User user = objectMapper.readValue(exchange.getRequestBody(), User.class);
            this.dbService.addUser(user);
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
        }
    }
}
