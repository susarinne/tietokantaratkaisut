package com.example.jpa.dao;

import com.example.jpa.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/*
 * Create a LibraryDAO class to handle CRUD operations for Student, Book, and BorrowedBook.
 */
public class LibraryDAO {
    private static final Logger logger = LoggerFactory.getLogger(LibraryDAO.class);
    private EntityManagerFactory emf;

    public LibraryDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }

    // Generic save method
    private <T> void saveEntity(Class<T> entityClass, T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            logger.info("{} entity saved: {}", entityClass.getSimpleName(), entity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error saving {} entity: {}", entityClass.getSimpleName(), entity, e);
        } finally {
            em.close();
        }
    }

    // Generic find method
    private <T> T findEntity(Class<T> entityClass, Long id) {
        EntityManager em = emf.createEntityManager();
        T entity = em.find(entityClass, id);
        em.close();
        logger.info("{} entity found: {}", entityClass.getSimpleName(), entity);
        return entity;
    }

    // Generic update method
    private <T> void updateEntity(Class<T> entityClass, T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            logger.info("{} entity updated: {}", entityClass.getSimpleName(), entity);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error updating {} entity: {}", entityClass.getSimpleName(), entity, e);
        } finally {
            em.close();
        }
    }

    // Generic delete method
    private <T> void deleteEntity(Class<T> entityClass, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                logger.info("{} entity deleted: {}", entityClass.getSimpleName(), entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error deleting {} entity with id: {}", entityClass.getSimpleName(), id, e);
        } finally {
            em.close();
        }
    }

    // Specific save methods
    public void saveStudent(Student student) {
        saveEntity(Student.class, student);
    }

    public void saveBook(Book book) {
        saveEntity(Book.class, book);
    }

    public void saveBorrowedBook(BorrowedBook borrowedBook) {
        saveEntity(BorrowedBook.class, borrowedBook);
    }

    public void saveBiography(Biography biography) {
        saveEntity(Biography.class, biography);
    }

    public void saveAuthor(Author author) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Merge the biography to ensure it is managed
            Biography biography = author.getBiography();
            if (biography != null) {
                logger.info("Merging biography: {}", biography.getDescription());
                biography = em.merge(biography);
                author.setBiography(biography);
            }
            logger.info("Persisting author: {}", author.getName());
            em.persist(author);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Error saving author: {}", author, e);
        } finally {
            em.close();
        }
    }

    // Specific find methods
    public Student getStudentById(Long id) {
        return findEntity(Student.class, id);
    }

    public Book getBookById(Long id) {
        return findEntity(Book.class, id);
    }

    public List<Book> getBooksBorrowedByStudent(Long studentId) {
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery(
                "SELECT b FROM BorrowedBook bb JOIN bb.book b WHERE bb.student.id = :studentId", Book.class)
                .setParameter("studentId", studentId)
                .getResultList();
        em.close();
        return books;
    }

    public Biography getBiographyById(Long id) {
        return findEntity(Biography.class, id);
    }

    public Author getAuthorById(Long id) {
        return findEntity(Author.class, id);
    }

    public List<Student> findAllStudents() {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        em.close();
        logger.info("All students: {}", students);
        return students;
    }

    public List<Book> findAllBooks() {
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        em.close();
        return books;
    }

    // Specific update methods
    public void updateStudent(Student student) {
        updateEntity(Student.class, student);
    }

    public void updateBook(Book book) {
        updateEntity(Book.class, book);
    }

    public void updateBorrowedBook(BorrowedBook borrowedBook) {
        updateEntity(BorrowedBook.class, borrowedBook);
    }

    // Specific delete methods
    public void deleteStudent(Long studentId) {
        deleteEntity(Student.class, studentId);
    }

    public void deleteBook(Long bookId) {
        deleteEntity(Book.class, bookId);
    }

    public void deleteBorrowedBook(Long borrowedBookId) {
        deleteEntity(BorrowedBook.class, borrowedBookId);
    }

}
