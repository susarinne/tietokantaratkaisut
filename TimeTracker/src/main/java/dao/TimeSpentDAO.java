package dao;

import jakarta.persistence.*;
import entity.*;
import java.util.List;

public class TimeSpentDAO {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TrackerPU");

	public void createTimeSpent(TimeSpent timeSpent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(timeSpent);
        em.getTransaction().commit();
        em.close();
    }

    public TimeSpent findById(Long id) {
        EntityManager em = emf.createEntityManager();
        TimeSpent timeSpent = em.find(TimeSpent.class, id);
        em.close();
        return timeSpent;
    }

    public List<TimeSpent> findAll() {
        EntityManager em = emf.createEntityManager();
        List<TimeSpent> timesSpent = em.createQuery("SELECT ts FROM TimeSpent ts", TimeSpent.class).getResultList();
        em.close();
        return timesSpent;
    }

    public void updateTimeSpent(TimeSpent timeSpent) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(timeSpent);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteTimeSpent(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TimeSpent timeSpent = em.find(TimeSpent.class, id);
        if (timeSpent != null) {
            em.remove(timeSpent);
        }
        em.getTransaction().commit();
        em.close();
    }

}
