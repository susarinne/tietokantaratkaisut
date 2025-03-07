import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import dao.*;
import model.*;
import converter.AikidoRank;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create the EntityManagerFactory and EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aikidoPU");
        EntityManager em = emf.createEntityManager();

        // Start a transaction
        em.getTransaction().begin();

        // Initialize DAO classes
        StudentDAO studentDAO = new StudentDAO(em);
        InstructorDAO instructorDAO = new InstructorDAO(em);
        ProgressReportDAO reportDAO = new ProgressReportDAO(em);

        // Add sample students
        Student student1 = new Student("John Doe", "john@example.com", AikidoRank.KYU_5, LocalDate.now());
        Student student2 = new Student("Jane Smith", "jane@example.com", AikidoRank.KYU_3, LocalDate.now().minusMonths(7));
        Student blackBeltStudent = new Student("Master Sensei", "sensei@example.com", AikidoRank.DAN_2, LocalDate.now().minusYears(5));

        // Save students using the DAO (no need to call em.persist directly)
        studentDAO.save(student1);
        studentDAO.save(student2);
        studentDAO.save(blackBeltStudent);

        // Add an instructor
        Instructor instructor = new Instructor("Sensei Aki", "Aikido Throws", 10);
        instructorDAO.save(instructor);

        // Commit the transaction
        em.getTransaction().commit();

        // Fetch and print students
        List<Student> students = studentDAO.findAll();
        System.out.println("Students:");
        students.forEach(s -> {
            System.out.println(s.getName() + " - " + s.getRank());
            System.out.println("Created At: " + s.getCreatedAt());
            System.out.println("Membership Duration: " + s.getMembershipDuration() + " years");
        });

        // Fetch and print instructors
        List<Instructor> instructors = instructorDAO.findAll();
        System.out.println("Instructors:");
        instructors.forEach(i -> System.out.println(i.getName() + " - " + i.getSpecialization()));

        // Create a new progress report for student1
        ProgressReport report = new ProgressReport(
            student1,
            LocalDate.now(),
            "Good progress, keep it up!",
            "Work on your balance");
        try {
            em.getTransaction().begin();
            reportDAO.save(report);
            em.getTransaction().commit();
            System.out.println("Progress report saved successfully.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("An error occurred while saving the progress report.");
            e.printStackTrace();
        }

        /*
         * JPQL Queries
         */
        System.out.println("\n----- JPQL Queries -----\n");
        // 1. Retrieve all training sessions attended by a specific student
        System.out.println("\n1. Training sessions attended by John Doe:");
        Long studentId = student1.getId();

        List<TrainingSession> studentSessions = em.createQuery(
                "SELECT ts FROM TrainingSession ts JOIN ts.attendances a " +
                        "WHERE a.student.id = :studentId",
                TrainingSession.class)
                .setParameter("studentId", studentId)
                .getResultList();

        studentSessions.forEach(session -> {
            System.out.println("Date: " + session.getDate() +
                    ", Location: " + session.getLocation() +
                    ", Instructor: " + session.getInstructor().getName());
        });

        // 2. List all students who have a specific rank
        System.out.println("\nStudents with a black belt ranks (Dan 1-4):");

        List<Student> blackBeltStudents = em.createQuery(
        "SELECT s FROM Student s WHERE s.rank IN :ranks", Student.class)
        .setParameter("ranks", Arrays.asList(
            AikidoRank.DAN_1, AikidoRank.DAN_2, AikidoRank.DAN_3, AikidoRank.DAN_4))
        .getResultList();

        blackBeltStudents.forEach(s -> {
            System.out.println(s.getName() + ", Rank: " + s.getRank());
        });

        // 3. Get all instructors specialized in a particular technique
        System.out.println("\n3. Instructors specialized in Aikido Throws:");
        String specialization = "Throws";

        List<Instructor> specInstructors = em.createQuery(
                "SELECT i FROM Instructor i WHERE i.specialization = :spec", Instructor.class)
                .setParameter("spec", specialization)
                .getResultList();

        specInstructors.forEach(i -> {
            System.out.println(i.getName() + ", Years of experience: " + i.getExperienceYears());
        });

        // 4. Fetch students with progress reports in the last three months
        System.out.println("\n4. Students with progress reports in the last 3 months:");
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        List<Student> studentsWithRecentReports = em.createQuery(
                "SELECT DISTINCT s FROM Student s JOIN s.progressReports pr " +
                        "WHERE pr.reportDate >= :startDate",
                Student.class)
                .setParameter("startDate", threeMonthsAgo)
                .getResultList();

        studentsWithRecentReports.forEach(s -> {
            System.out.println(s.getName() + " - " + s.getRank());
        });
        // End of JPQL queries

        /*
         * Criteria API Queries
         */

        // 1. Find all students who joined within the last six months
        System.out.println("\n1. Students who joined within the last six months:");
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        CriteriaBuilder studentBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Student> studentQuery = studentBuilder.createQuery(Student.class);
        Root<Student> studentRoot = studentQuery.from(Student.class);
        studentQuery.select(studentRoot)
                .where(studentBuilder.greaterThanOrEqualTo(studentRoot.get("joinDate"), sixMonthsAgo));
        List<Student> recentStudents = em.createQuery(studentQuery).getResultList();

        recentStudents.forEach(s -> {
            System.out.println(s.getName() + " joined on " + s.getJoinDate());
        });

        // 2. Search for training sessions held in a specific location
        System.out.println("\n2. Training sessions held in 'Kotidojo':");
        String searchLocation = "Kotidojo";

        CriteriaBuilder sessionBuilder = em.getCriteriaBuilder();
        CriteriaQuery<TrainingSession> sessionQuery = sessionBuilder.createQuery(TrainingSession.class);
        Root<TrainingSession> sessionRoot = sessionQuery.from(TrainingSession.class);
        sessionQuery.select(sessionRoot)
                .where(sessionBuilder.equal(sessionRoot.get("location"), searchLocation));
        List<TrainingSession> sessionLocations = em.createQuery(sessionQuery).getResultList();

        sessionLocations.forEach(ts -> {
            System.out.println("Training session on " + ts.getDate() +
                    ", Duration: " + ts.getDuration() + " minutes, " +
                    "Instructor: "
                    + (ts.getInstructor() != null ? ts.getInstructor().getName() : "None"));
        });

        // 3. Retrieve all instructors with more than five years of experience
        System.out.println("\n3. Instructors with more than 5 years of experience:");
        int minExperience = 5;

        CriteriaBuilder instructorBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Instructor> instructorQuery = instructorBuilder.createQuery(Instructor.class);
        Root<Instructor> instructorRoot = instructorQuery.from(Instructor.class);
        instructorQuery.select(instructorRoot)
                .where(instructorBuilder.greaterThan(instructorRoot.get("experienceYears"), minExperience));
        List<Instructor> experiencedInstructors = em.createQuery(instructorQuery).getResultList();

        experiencedInstructors.forEach(i -> {
            System.out.println(i.getName() + " has " + i.getExperienceYears() +
                    " years of experience, specialized in " + i.getSpecialization());
        });

        // Close the EntityManager and EntityManagerFactory
        em.close();
        emf.close();
    }

}
