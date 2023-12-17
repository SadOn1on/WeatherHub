package com.weatherrestapi;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.Date;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {

        try (LoginDataGateway loginDataGateway = new LoginDataGateway("jdbc:postgresql:Weather_app")) {
            if (!loginDataGateway.checkAuth(username, password)) {
                throw new SQLException("no such user");
            }
        } catch (Exception e) {
            return Response.status(401).build();
        }

        String token = Jwts.builder()
                .setSubject(username)
                .claim("roles", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS256, "afasfasdfasfdasdfasdfasdfasdfafasfasdfasfdasdfasdfasdfasdfafasfa")
                .compact();

        return Response.ok(token).build();
    }
}