package com.weatherrestapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Properties;

@Path("/realWeather")
public class ForecastResource {

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

    @GET
    @Path("/currentWeather")
    @Produces("text/plain")
    public String getCurrentWeather(@QueryParam("units") String units,
                        @QueryParam("cityName") String cityName) {
        try {
        Map<String, Double> citiesGeo = TownGeo.getTownGeo(cityName);
        String lat = citiesGeo.get("lat").toString();
        String lon = citiesGeo.get("lon").toString();
        String uri = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
                "&lon=" + lon + "&appid=" + properties.getProperty("api_key") + "&units=" + units;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @GET
    @Path("/forecast")
    @Produces("text/plain")
    public String getForecast(@QueryParam("units") String units,
                                      @QueryParam("cityName") String cityName) {
        try {
        Map<String, Double> citiesGeo = TownGeo.getTownGeo(cityName);
        String lat = citiesGeo.get("lat").toString();
        String lon = citiesGeo.get("lon").toString();
        String uri = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat +
                "&lon=" + lon + "&appid=" + properties.getProperty("api_key")  + "&units=" + units;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
