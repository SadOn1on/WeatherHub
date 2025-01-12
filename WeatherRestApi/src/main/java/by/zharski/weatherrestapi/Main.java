package by.zharski.weatherrestapi;

import by.zharski.weatherrestapi.controller.AuthController;
import by.zharski.weatherrestapi.controller.DBWeatherController;
import by.zharski.weatherrestapi.controller.ForecastController;
import by.zharski.weatherrestapi.security.AuthorizationFilter;
import by.zharski.weatherrestapi.security.CorsFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Main extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CorsFilter.class);
        classes.add(AuthorizationFilter.class);
        classes.add(ForecastController.class);
        classes.add(DBWeatherController.class);
        classes.add(AuthController.class);
        return classes;
    }
}