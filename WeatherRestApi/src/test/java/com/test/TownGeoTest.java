package com.test;

import com.weatherrestapi.TownGeo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TownGeoTest {

    Double minskExpectedLat = 53.9024716;
    Double minskExpectedLon = 27.5618225;
    Double londonExpectedLat = 51.5073219;
    Double londonExpectedLon = -0.1276474;

    @Test
    public void testGetTownGeo() {
        // Test case for Minsk
        String minsk = "Minsk";
        Map<String, Double> minskCoords = TownGeo.getTownGeo(minsk);
        assertEquals(minskExpectedLat, minskCoords.get("lat"), 0.0001);
        assertEquals(minskExpectedLon, minskCoords.get("lon"), 0.0001);

        // Test case for London
        String london = "London";
        Map<String, Double> londonCoords = TownGeo.getTownGeo(london);
        assertEquals(londonExpectedLat, londonCoords.get("lat"), 0.0001);
        assertEquals(londonExpectedLon, londonCoords.get("lon"), 0.0001);
    }

    @Test
    public void testGetTownGeoInvalidTown() {
        // Test case for an invalid town name
        String invalidTown = "InvalidTown";
        Exception thrown = assertThrows(IllegalArgumentException.class,
                () -> TownGeo.getTownGeo(invalidTown)
        );
        assertEquals(thrown.getMessage(), "Invalid town name");
    }

    @Test
    public void testGetTownGeoInvalidURI() {
        // Test case for a JsonMappingException
        String invalidJson = "{\"lat\":53.9024716";
        Exception thrown = assertThrows(RuntimeException.class,
                () -> TownGeo.getTownGeo(invalidJson)
        );
        assertEquals(thrown.getMessage(), "Wrong URI");
    }

    @Test
    public void testGetTownGeoEmptyName() {
         Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> TownGeo.getTownGeo("")
        );
        assertEquals(thrown.getMessage(), "Empty town name");
    }
}

