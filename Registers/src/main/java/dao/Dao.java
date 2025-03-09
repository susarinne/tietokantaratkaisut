package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import entity.*;
import java.util.*;

public class Dao {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("RegistersPU");

	public void addRegister(Register reg) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
        em.persist(reg);
        em.getTransaction().commit();
        em.close();
	}
	
	public void addEvent(int eventNumber, int regNumber, double amount) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
        Register reg = em.find(Register.class, regNumber);
        SalesEvent evt = new SalesEvent(eventNumber, reg, amount);
        em.persist(evt);
        em.getTransaction().commit();
        em.close();	
	}
	
	// JPQL Queries
	public List<SalesEvent> retrieveSmallSalesJPQL(double limit) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String jpql = "SELECT s FROM SalesEvent s WHERE s.amount < :limit";
		TypedQuery<SalesEvent> query = em.createQuery(jpql, SalesEvent.class);
		query.setParameter("limit", limit);
		List<SalesEvent> result = query.getResultList();
		em.getTransaction().commit();
		em.close();
		return result;
	}
	
	public void addServiceFeeJPQL(double fee) {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    String jpql = "UPDATE SalesEvent s SET s.amount = s.amount + :fee";
	    Query query = em.createQuery(jpql);
	    query.setParameter("fee", fee);
	    query.executeUpdate();
	    em.getTransaction().commit();
	    em.close();
	}
	
	public void deleteAllSalesEventsJPQL() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    String jpql = "DELETE FROM SalesEvent";
	    Query query = em.createQuery(jpql);
	    query.executeUpdate();
	    em.getTransaction().commit();
	    em.close();
	}
	
	
	// Criteria API Queries
	public List<SalesEvent> retrieveSmallSalesCriteria(double limit) {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<SalesEvent> cq = cb.createQuery(SalesEvent.class);
	    Root<SalesEvent> salesEvent = cq.from(SalesEvent.class);
	    cq.select(salesEvent).where(cb.lessThan(salesEvent.get("amount"), limit));
	    List<SalesEvent> smallSales = em.createQuery(cq).getResultList();
	    em.getTransaction().commit();
	    em.close();
	    return smallSales;
	}

	public void addServiceFeeCriteria(double fee) {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaUpdate<SalesEvent> update = cb.createCriteriaUpdate(SalesEvent.class);
	    Root<SalesEvent> salesEvent = update.from(SalesEvent.class);
	    // So that values are interpreted as double:
	    Path<Double> salesAmount = salesEvent.get("amount");
	    update.set(salesAmount, cb.sum(salesAmount, fee));
	    em.createQuery(update).executeUpdate();
	    em.getTransaction().commit();
	    em.close();
	}

	public void deleteAllSalesEventsCriteria() {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaDelete<SalesEvent> delete = cb.createCriteriaDelete(SalesEvent.class);
	    delete.from(SalesEvent.class);
	    em.createQuery(delete).executeUpdate();
	    em.getTransaction().commit();
	    em.close();
	}
	
}
