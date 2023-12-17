package com.weatherrestapi;

public class ConverterClass {

    private ConverterClass() {

    };

    static public Double fahrenheitToCelsius(Double fahrenheit) {
        return ((fahrenheit-32)*5)/9;
    }

    static public Double celsiusToFahrenheit(Double celsius) {
        return ((celsius*9)/5)+32;
    }

    static public Double mphToMs(Double mph) {
        return (mph / 2.23694);
    }

    static public Double msToMph(Double ms) {
        return (ms * 2.23694);
    }
}
