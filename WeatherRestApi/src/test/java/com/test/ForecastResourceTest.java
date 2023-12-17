package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherrestapi.ForecastResource;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForecastResourceTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(ForecastResource.class);
    }

    @Test
    public void testGetCurrentWeather() {
        Response response = target("/realWeather/currentWeather")
                .queryParam("units", "metric")
                .queryParam("cityName", "London")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("text/plain", response.getMediaType().toString());

        String responseBody = response.readEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode json = objectMapper.readTree(responseBody);

            // Verify the structure of the JSON object
            assertTrue(json.has("coord"));
            assertTrue(json.has("weather"));
            assertTrue(json.has("base"));
            assertTrue(json.has("main"));
            assertTrue(json.has("visibility"));
            assertTrue(json.has("wind"));
            assertTrue(json.has("clouds"));
            assertTrue(json.has("dt"));
            assertTrue(json.has("sys"));
            assertTrue(json.has("timezone"));
            assertTrue(json.has("id"));
            assertTrue(json.has("name"));
            assertTrue(json.has("cod"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }

    @Test
    public void testGetForecast() {
        Response response = target("/realWeather/forecast")
                .queryParam("units", "metric")
                .queryParam("cityName", "London")
                .request()
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("text/plain", response.getMediaType().toString());

        String responseBody = response.readEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode json = objectMapper.readTree(responseBody);

            // Verify the structure of the JSON object
            assertTrue(json.has("city"));
            assertTrue(json.has("cod"));
            assertTrue(json.has("message"));
            assertTrue(json.has("cnt"));
            assertTrue(json.has("list"));
            assertEquals(Response.Status.OK.getStatusCode(), json.get("cod").asInt());

            JsonNode cityNode = json.get("city");
            assertTrue(cityNode.has("id"));
            assertTrue(cityNode.has("name"));
            assertTrue(cityNode.has("coord"));
            assertTrue(cityNode.has("country"));
            assertTrue(cityNode.has("population"));

            JsonNode listNode = json.get("list");
            assertTrue(listNode.isArray());

            // Add additional assertions for the structure of each forecast item in the list
            for (JsonNode forecastItem : listNode) {
                assertTrue(forecastItem.has("dt"));
                assertTrue(forecastItem.has("main"));
                assertTrue(forecastItem.has("weather"));
                assertTrue(forecastItem.has("clouds"));
                assertTrue(forecastItem.has("wind"));
                assertTrue(forecastItem.has("sys"));
                assertTrue(forecastItem.has("dt_txt"));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }
}
