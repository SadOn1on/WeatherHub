package by.zharski.weatherrestapi.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="weather_record")
public class WeatherRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    int id;
    String city;
    Date date;
    Double temperature;
    Double humidity;
    Integer windDirection;
    String clouds;
    Double windSpeed;

    public WeatherRecord() {
    }

    public WeatherRecord(String city, Date date, Double temperature, Double humidity,
                         Integer windDirection, String clouds, Double windSpeed) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
