package by.zharski.weatherrestapi.test.security;

import by.zharski.weatherrestapi.security.AuthorizationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.*;

class AuthorizationFilterTest {
    private AuthorizationFilter authorizationFilter;
    private static final String TOKEN_SECRET = "afasfasdfasfdasdfasdfasdfasdfafasfasdfasfdasdfasdfasdfasdfafasfa";
    private static String validToken;


    @Mock
    private ContainerRequestContext mockRequestContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authorizationFilter = new AuthorizationFilter();
        when(mockRequestContext.getHeaders()).thenReturn(new MultivaluedHashMap<>());
        validToken = Jwts.builder()
                .setSubject("username")
                .claim("roles", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    @Test
    void testFilter_ValidToken() throws Exception {
        when(mockRequestContext.getHeaderString("authentication")).thenReturn("Bearer " + validToken);

        authorizationFilter.filter(mockRequestContext);

        // Verify that the request context is not aborted
        verify(mockRequestContext, never()).abortWith(any(Response.class));
    }

    @Test
    void testFilter_InvalidToken() throws Exception {
        String invalidToken = "invalid_token";
        when(mockRequestContext.getHeaderString("authentication")).thenReturn("Bearer " + invalidToken);

        authorizationFilter.filter(mockRequestContext);

        // Verify that the request context is aborted with UNAUTHORIZED status
        verify(mockRequestContext).abortWith(
                argThat(response -> response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testFilter_MissingToken() throws Exception {
        when(mockRequestContext.getHeaderString("authentication")).thenReturn(null);

        authorizationFilter.filter(mockRequestContext);

        // Verify that the request context is aborted with UNAUTHORIZED status
        verify(mockRequestContext).abortWith(
                argThat(response -> response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testFilter_TokenWithoutAdminRole() throws Exception {
        String tokenWithoutAdminRole = "token_without_admin_role";
        when(mockRequestContext.getHeaderString("authentication")).thenReturn("Bearer " + tokenWithoutAdminRole);

        authorizationFilter.filter(mockRequestContext);

        // Verify that the request context is aborted with UNAUTHORIZED status
        verify(mockRequestContext).abortWith(
                argThat(response -> response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }
}
