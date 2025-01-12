package by.zharski.weatherrestapi.test;

import by.zharski.weatherrestapi.Main;
import by.zharski.weatherrestapi.controller.AuthController;
import by.zharski.weatherrestapi.controller.DBWeatherController;
import by.zharski.weatherrestapi.controller.ForecastController;
import by.zharski.weatherrestapi.security.AuthorizationFilter;
import by.zharski.weatherrestapi.security.CorsFilter;
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
        expectedClasses.add(ForecastController.class);
        expectedClasses.add(DBWeatherController.class);
        expectedClasses.add(AuthController.class);
        Main testAppl = new Main();
        assertEquals(expectedClasses, testAppl.getClasses());
    }
}
