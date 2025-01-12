package by.zharski.weatherrestapi.utill;

public class ConverterClass {

    private ConverterClass() {}

    public static Double fahrenheitToCelsius(Double fahrenheit) {
        return ((fahrenheit-32)*5)/9;
    }

    public static Double celsiusToFahrenheit(Double celsius) {
        return ((celsius*9)/5)+32;
    }

    public static Double mphToMs(Double mph) {
        return (mph / 2.23694);
    }

    public static Double msToMph(Double ms) {
        return (ms * 2.23694);
    }
}
