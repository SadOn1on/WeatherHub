package com.weatherrestapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class TownGeo {
    private TownGeo() {
    }

    static public HashMap<String, Double> getTownGeo(String townName) throws IllegalArgumentException{
        if (townName.length() == 0) {
            throw new IllegalArgumentException("Empty town name");
        }
        String uri = "https://api.openweathermap.org/geo/1.0/direct?q="
                + townName + "&appid=4da1c26f16a48ca71490132fd31679f8&limit=1";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            if (response.body().equals("[]")) {
                throw new IllegalArgumentException("Invalid town name");
            }
            JsonNode nameNode = mapper.readTree(response.body().substring(1, response.body().length() - 1));
            HashMap<String, Double> geoResult = new HashMap<>();
            geoResult.put("lat", nameNode.get("lat").asDouble());
            geoResult.put("lon", nameNode.get("lon").asDouble());
            return geoResult;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Wrong URI");
        } catch (IOException | InterruptedException e ) {
            throw new RuntimeException("Error accessing location api");
        }
    }
}
