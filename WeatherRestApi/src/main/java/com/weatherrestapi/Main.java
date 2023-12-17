package com.weatherrestapi;

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
        classes.add(ForecastResource.class);
        classes.add(DBWeatherResource.class);
        classes.add(AuthResource.class);
        return classes;
    }
}