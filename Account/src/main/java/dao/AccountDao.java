package dao;

import model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;

public class AccountDao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("accountPU");

	// Persist object if it doesn't exist in database, otherwise do nothing
    public void save(Account account) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Account existingAccount = em.find(Account.class, account.getNumber());
        if (existingAccount == null) {
        	// Account doesn't exist – create new
            em.persist(account);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public Account find(int number) {
        EntityManager em = emf.createEntityManager();
        Account account = em.find(Account.class, number);
        em.close();
        return account;
    }
    
    public void transfer(int sourceAccountNumber, int destinationAccountNumber, double amount) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        try {
            Account sourceAccount = em.find(Account.class, sourceAccountNumber);
            Account destinationAccount = em.find(Account.class, destinationAccountNumber);
            
            if (sourceAccount.getBalance() < amount) {
                throw new IllegalArgumentException("Not enough balance");
            }
            
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
            em.getTransaction().commit();
        } catch (OptimisticLockException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}
