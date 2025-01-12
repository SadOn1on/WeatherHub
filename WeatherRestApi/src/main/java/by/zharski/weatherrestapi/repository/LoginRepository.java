package by.zharski.weatherrestapi.repository;

import by.zharski.weatherrestapi.entity.User;
import by.zharski.weatherrestapi.utill.EntityManagerFactoryHolder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class LoginRepository {

    public boolean checkAuth(String username, byte[] password) {
        try (EntityManager entityManager = EntityManagerFactoryHolder.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT u FROM  User u WHERE name = :username", User.class);
            query.setParameter("username", username);
            List<User> queryResultList = query.getResultList();
            if (queryResultList.isEmpty()) {
                return false;
            }

            User user = queryResultList.get(0);

            entityManager.getTransaction().commit();

            return Arrays.equals(hashPassword(password, user.getSalt()), user.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void putNewLogin(String username, byte[] password) {
        try (EntityManager entityManager = EntityManagerFactoryHolder.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);
            byte[] dbPassword = hashPassword(password, salt);
            User user = new User(username, salt, dbPassword);

            entityManager.persist(user);

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private byte[] hashPassword(byte[] password, byte[] salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return md.digest(password);
    }

}
