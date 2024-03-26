package com.weatherrestapi;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @Column(unique = true)
    String name;
    byte[] salt;
    byte[] password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    List<WeatherRecord> weatherRecords;

    public User() {

    }

    public User(String name, byte[] salt, byte[] password) {
        this.name = name;
        this.salt = salt;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public List<WeatherRecord> getWeatherRecords() {
        return weatherRecords;
    }

    public void setWeatherRecords(List<WeatherRecord> weatherRecords) {
        this.weatherRecords = weatherRecords;
    }

}
