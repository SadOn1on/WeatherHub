package com.weatherrestapi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

@Path("/auth")
public class AuthResource {

    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = WeatherDataGateway.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Sorry, unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {

        try {
            LoginDataGateway loginDataGateway = new LoginDataGateway();
            if (!loginDataGateway.checkAuth(username, password.getBytes())) {
                throw new SQLException("no such user");
            }
        } catch (Exception e) {
            return Response.status(401).build();
        }

        String token = Jwts.builder()
                .setSubject(username)
                .claim("roles", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS256, properties.getProperty("sign_key"))
                .compact();

        return Response.ok(token).build();
    }
}