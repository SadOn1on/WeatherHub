package com.weatherrestapi;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class WeatherDataGateway implements AutoCloseable{
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

    public WeatherDataGateway(String url) throws SQLException {
        this.url = url;
        connection = DriverManager.getConnection(url, properties);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean postData(WeatherRecord weatherRecord, String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE name=?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Integer userID = rs.getInt(1);
            stmt = connection.prepareStatement("INSERT INTO weather_data " +
                    "(city, user_id, date, temperature, humidity, clouds, wind_speed, wind_direction) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate = dateFormat.parse(weatherRecord.getDate());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            stmt.setString(1, weatherRecord.getCity());
            stmt.setInt(2, userID);
            stmt.setDate(3, sqlDate);
            stmt.setDouble(4, weatherRecord.getTemperature());
            stmt.setDouble(5, weatherRecord.getHumidity());
            stmt.setString(6, weatherRecord.getClouds());
            stmt.setDouble(7, weatherRecord.getWindSpeed());
            stmt.setInt(8, weatherRecord.getWindDirection());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public ArrayList<WeatherRecord> getData(String userName, String city, String date) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE name=?");
        stmt.setString(1, userName);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Integer userID = rs.getInt(1);

        stmt = connection.prepareStatement("SELECT * FROM weather_data " +
                "WHERE (userID=? AND date=? AND city=?)");
        stmt.setInt(1, userID);
        stmt.setString(2, date);
        stmt.setString(3, city);
        rs = stmt.executeQuery();

        ArrayList<WeatherRecord> result = new ArrayList<>();
        WeatherRecord record = new WeatherRecord();
        while (rs.next()) {
            record.setUserID(userID);
            record.setCity(city);
            record.setDate(date);
            record.setTemperature(rs.getDouble(5));
            record.setHumidity(rs.getDouble(6));
            record.setClouds(rs.getString(7));
            record.setWindSpeed(rs.getDouble(8));
            record.setWindDirection(rs.getInt(9));

            result.add(record);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
