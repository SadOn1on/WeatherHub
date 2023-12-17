package com.weatherrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WeatherRecord {
    String city;
    String date;
    Double temperature;
    Double humidity;
    Integer userID;
    String userName;
    Integer windDirection;
    String clouds;
    Double windSpeed;

    public WeatherRecord() {
    }

    public WeatherRecord(String city, String date, Double temperature, Double humidity, String userName,
                         Integer windDirection, String clouds, Double windSpeed) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.userName = userName;
        this.windDirection = windDirection;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
    }

    public WeatherRecord(String city, String date, Double temperature, Double humidity, Integer userID,
                         String userName, Integer windDirection, String clouds, Double windSpeed) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.userID = userID;
        this.userName = userName;
        this.windDirection = windDirection;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public ObjectNode toJSONNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, ObjectNode.class);
    }


}
