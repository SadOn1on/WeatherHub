package by.zharski.weatherrestapi.repository;

import by.zharski.weatherrestapi.entity.User;
import by.zharski.weatherrestapi.entity.WeatherRecord;
import by.zharski.weatherrestapi.utill.EntityManagerFactoryHolder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {

    public boolean postData(WeatherRecord weatherRecord, String username) {
        try (EntityManager entityManager = EntityManagerFactoryHolder.getEntityManagerFactory().createEntityManager()) {

            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name");
            query.setParameter("name", username);
            List<User> users = query.getResultList();
            User user = users.get(0);
            user.getWeatherRecords().add(weatherRecord);

            entityManager.persist(user);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public List<WeatherRecord> getData(String userName, String city, Date date) {
        try (EntityManager entityManager = EntityManagerFactoryHolder.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT w FROM User u " +
                    "LEFT JOIN u.weatherRecords w " +
                    "WHERE u.name = :userName AND w.city = :city AND w.date = :date", WeatherRecord.class);
            query.setParameter("userName", userName);
            query.setParameter("city", city);
            query.setParameter("date", date);
            List<WeatherRecord> result = query.getResultList();

            entityManager.getTransaction().commit();

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

}
