package by.zharski.weatherrestapi.test.utill;

import by.zharski.weatherrestapi.utill.ConverterClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterClassTest {

    @Test
    public void testFahrenheitToCelsius() {
        // Test case for converting 32°F to Celsius
        double fahrenheit = 32.0;
        double expectedCelsius = 0.0;
        double actualCelsius = ConverterClass.fahrenheitToCelsius(fahrenheit);
        assertEquals(expectedCelsius, actualCelsius, 0.0001);
    }

    @Test
    public void testFahrenheitToCelsiusNegativeValue() {
        double fahrenheit = -4.0;
        double expectedCelsius = -20.0;

        double actualCelsius = ConverterClass.fahrenheitToCelsius(fahrenheit);

        assertEquals(expectedCelsius, actualCelsius, 0.001);
    }

    @Test
    public void testFahrenheitToCelsiusZeroValue() {
        double fahrenheit = 0.0;
        double expectedCelsius = -17.7778;

        double actualCelsius = ConverterClass.fahrenheitToCelsius(fahrenheit);

        assertEquals(expectedCelsius, actualCelsius, 0.001);
    }

    @Test
    public void testCelsiusToFahrenheit() {
        // Test case for converting 0°C to Fahrenheit
        double celsius = 0.0;
        double expectedFahrenheit = 32.0;
        double actualFahrenheit = ConverterClass.celsiusToFahrenheit(celsius);
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.0001);
    }

    @Test
    public void testCelsiusToFahrenheitNegativeValue() {
        double celsius = -20.0;
        double expectedFahrenheit = -4.0;

        double actualFahrenheit = ConverterClass.celsiusToFahrenheit(celsius);

        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001);
    }

    @Test
    public void testCelsiusToFahrenheitZeroValue() {
        double celsius = 0.0;
        double expectedFahrenheit = 32.0;

        double actualFahrenheit = ConverterClass.celsiusToFahrenheit(celsius);

        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001);
    }

    @Test
    public void testMphToMs() {
        // Test case for converting 60 mph to m/s
        double mph = 60.0;
        double expectedMs = 26.8224;
        double actualMs = ConverterClass.mphToMs(mph);
        assertEquals(expectedMs, actualMs, 0.0001);
    }

    @Test
    public void testMphToMsZeroValue() {
        double mph = 0.0;
        double expectedMs = 0.0;

        double actualMs = ConverterClass.mphToMs(mph);

        assertEquals(expectedMs, actualMs, 0.001);
    }

    @Test
    public void testMsToMph() {
        // Test case for converting 30 m/s to mph
        double ms = 30.0;
        double expectedMph = 67.1082;
        double actualMph = ConverterClass.msToMph(ms);
        assertEquals(expectedMph, actualMph, 0.0001);
    }

    @Test
    public void testMsToMphNegativeValue() {
        double ms = -2.0;
        double expectedMph = -4.47388;

        double actualMph = ConverterClass.msToMph(ms);

        assertEquals(expectedMph, actualMph, 0.001);
    }

    @Test
    public void testFahrenheitToCelsiusNegative() {
        // Test case for converting -40°F to Celsius
        double fahrenheit = -40.0;
        double expectedCelsius = -40.0;
        double actualCelsius = ConverterClass.fahrenheitToCelsius(fahrenheit);
        assertEquals(expectedCelsius, actualCelsius, 0.0001);
    }

    @Test
    public void testCelsiusToFahrenheitNegative() {
        // Test case for converting -10°C to Fahrenheit
        double celsius = -10.0;
        double expectedFahrenheit = 14.0;
        double actualFahrenheit = ConverterClass.celsiusToFahrenheit(celsius);
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.0001);
    }

    @Test
    public void testMphToMsZero() {
        // Test case for converting 0 mph to m/s
        double mph = 0.0;
        double expectedMs = 0.0;
        double actualMs = ConverterClass.mphToMs(mph);
        assertEquals(expectedMs, actualMs, 0.0001);
    }

    @Test
    public void testMsToMphZero() {
        // Test case for converting 0 m/s to mph
        double ms = 0.0;
        double expectedMph = 0.0;
        double actualMph = ConverterClass.msToMph(ms);
        assertEquals(expectedMph, actualMph, 0.0001);
    }
}
