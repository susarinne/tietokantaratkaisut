package dao;

import model.Student;
import converter.AikidoRank;
import jakarta.persistence.EntityManager;
import java.util.List;

public class StudentDAO extends GenericDAO<Student> {

    public StudentDAO(EntityManager entityManager) {
        super(Student.class, entityManager);
    }

    /**
     * Find all students with a specific Aikido rank
     */
    public List<Student> findByRank(AikidoRank rank) {
        return entityManager.createQuery(
            "SELECT s FROM Student s WHERE s.rank = :rank", 
            Student.class)
            .setParameter("rank", rank)
            .getResultList();
    }

}
