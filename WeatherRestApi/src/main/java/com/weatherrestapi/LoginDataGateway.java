package com.weatherrestapi;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class LoginDataGateway implements AutoCloseable{
    String url;
    Connection connection;

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

    public LoginDataGateway(String url) throws SQLException {
        this.url = url;
        connection = DriverManager.getConnection(url, properties);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean checkAuth(String username, String password) throws SQLException {
        byte[] salt, dbPassword;
        PreparedStatement stmt = connection.prepareStatement("SELECT salt, password FROM users WHERE name = ?");
        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.isLast()) {
            return false;
        } else {
            resultSet.next();
            salt = resultSet.getBytes(1);
            dbPassword = resultSet.getBytes(2);
        }

        return Arrays.equals(hashPassword(password, salt), dbPassword);
    }

    public void putNewLogin(String username, String password) throws SQLException {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        byte[] dbPassword = hashPassword(password, salt);

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO users (name, password, salt) VALUES (?, ?, ?)");
        stmt.setString(1, username);
        stmt.setBytes(2, dbPassword);
        stmt.setBytes(3, salt);
        stmt.executeUpdate();
    }

    private byte[] hashPassword(String password, byte[] salt) {
        byte[] passByte = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return md.digest(passByte);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
