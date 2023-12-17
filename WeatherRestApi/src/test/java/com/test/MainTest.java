package com.test;

import com.weatherrestapi.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    public void TestGetClass() {
        Set<Class<?>> expectedClasses = new HashSet<>();
        expectedClasses.add(CorsFilter.class);
        expectedClasses.add(AuthorizationFilter.class);
        expectedClasses.add(ForecastResource.class);
        expectedClasses.add(DBWeatherResource.class);
        expectedClasses.add(AuthResource.class);
        Main testAppl = new Main();
        assertEquals(expectedClasses, testAppl.getClasses());
    }
}
