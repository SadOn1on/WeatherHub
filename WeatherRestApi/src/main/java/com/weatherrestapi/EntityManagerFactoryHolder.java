package com.weatherrestapi;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class EntityManagerFactoryHolder {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("WeatherAPI");


    private EntityManagerFactoryHolder() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
