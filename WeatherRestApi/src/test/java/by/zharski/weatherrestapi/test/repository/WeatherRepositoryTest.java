package by.zharski.weatherrestapi.test.repository;

import by.zharski.weatherrestapi.controller.DBWeatherController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WeatherRepositoryTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(DBWeatherController.class);
    }

    private static final String TOKEN_SECRET = "afasfasdfasfdasdfasdfasdfasdfafasfasdfasfdasdfasdfasdfasdfafasfa";

    private static String token;

    @BeforeAll
    public static void setup() {
        // Generate the token for authentication
        token = Jwts.builder()
                .setSubject("Ilys")
                .claim("roles", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    @Test
    public void testPostWeatherToDB() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("city", "London");
        formData.add("date", "2023-05-22");
        formData.add("temperature", "15.5");
        formData.add("humidity", "60");
        formData.add("clouds", "Partly cloudy");
        formData.add("windSpeed", "5.8");
        formData.add("windDir", "180");
        formData.add("username", "Ilys");
        formData.add("units", "Imperial");

        Response response = target("/dbWeather")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                .post(Entity.form(formData));


        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetWeatherFromDB() {
        Response response = target("/dbWeather")
                .queryParam("username", "Ilys")
                .queryParam("date", "2023-05-22")
                .queryParam("city", "London")
                .queryParam("units", "Imperial")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.TEXT_PLAIN, response.getMediaType().toString());

        String result = response.readEntity(String.class);
        assertNotNull(result);
    }
}
