package by.zharski.weatherrestapi.test.controller;

import by.zharski.weatherrestapi.controller.AuthController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthController.class);
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Test valid username and password
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("username", "Ilys");
        formData.add("password", "1234");

        Response response = target("/auth/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());

        String token = response.readEntity(String.class);
        assertNotNull(token);

        // Verify the token
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey("afasfasdfasfdasdfasdfasdfasdfafasfasdfasfdasdfasdfasdfasdfafasfa")
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();

            assertEquals("Ilys", claims.getSubject());
            assertEquals("admin", claims.get("roles", String.class));
        } catch (Exception e) {
            // Handle the exception
            fail("Token was not verified");
        }


    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Test invalid username
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("username", "InvalidUser");
        formData.add("password", "1234");

        Response response = target("/auth/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

        // Test invalid password
        formData.clear();
        formData.add("username", "Ilys");
        formData.add("password", "InvalidPassword");

        response = target("/auth/login")
                .request()
                .post(Entity.form(formData));

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
