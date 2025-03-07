package dao;

import model.Student;
import model.TrainingSession;
import jakarta.persistence.EntityManager;
import java.util.List;


public class TrainingSessionDAO extends GenericDAO<TrainingSession> {

    public TrainingSessionDAO(EntityManager entityManager) {
        super(TrainingSession.class, entityManager);
    }

    /**
     * Find all training sessions attended by a specific student
     */
    public List<TrainingSession> findByStudent(Student student) {
        return entityManager.createQuery(
            "SELECT ts FROM TrainingSession ts JOIN ts.attendances a " +
            "WHERE a.student = :student", 
            TrainingSession.class)
            .setParameter("student", student)
            .getResultList();
    }

}
