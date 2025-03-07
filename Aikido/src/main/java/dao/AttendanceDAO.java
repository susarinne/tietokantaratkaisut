package dao;

import converter.AttendanceStatus;
import model.Attendance;
import model.Student;
import model.TrainingSession;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AttendanceDAO extends GenericDAO<Attendance> {

    public AttendanceDAO(EntityManager entityManager) {
        super(Attendance.class, entityManager);
    }

    /**
     * Find attendance records by student
     */
    public List<Attendance> findByStudent(Student student) {
        return entityManager.createQuery(
                "SELECT a FROM Attendance a WHERE a.student = :student",
                Attendance.class)
                .setParameter("student", student)
                .getResultList();
    }

    /**
     * Find attendance records by training session
     */
    public List<Attendance> findByTrainingSession(TrainingSession session) {
        return entityManager.createQuery(
                "SELECT a FROM Attendance a WHERE a.trainingSession = :session",
                Attendance.class)
                .setParameter("session", session)
                .getResultList();
    }

    /**
     * Find attendance records by status
     */
    public List<Attendance> findByStatus(AttendanceStatus status) {
        return entityManager.createQuery(
                "SELECT a FROM Attendance a WHERE a.status = :status",
                Attendance.class)
                .setParameter("status", status)
                .getResultList();
    }

}