package dao;

import model.ProgressReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;

public class ProgressReportDAO extends GenericDAO<ProgressReport> {

    public ProgressReportDAO(EntityManager entityManager) {
        super(ProgressReport.class, entityManager);
    }

    /**
     * Implement a mechanism to notify users of concurrent modification conflicts
     */
    @Override
    public void update(ProgressReport report) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            super.update(report);
            transaction.commit();
            System.out.println("Progress report updated successfully.");
        } catch (OptimisticLockException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("CONCURRENT MODIFICATION DETECTED!");
            System.out.println("The report was modified by another user.");
            System.out.println("Please try again.");
            throw e;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error updating progress report.");
        }
    }

}
