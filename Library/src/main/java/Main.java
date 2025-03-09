package com.example.jpa;

import com.example.jpa.dao.LibraryDAO;
import com.example.jpa.entity.*;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        // Load environment variables
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        // Create LibraryDAO instance
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");
        LibraryDAO dao = new LibraryDAO(emf);

        // Create biographies
        Biography bio1 = new Biography("Biography of Aleksis Kivi", null);
        Biography bio2 = new Biography("Biography of Mika Waltari", null);

        // Persist biographies
        dao.saveBiography(bio1);
        dao.saveBiography(bio2);

        // Refresh biographies to ensure they are managed
        bio1 = dao.getBiographyById(bio1.getId());
        bio2 = dao.getBiographyById(bio2.getId());

        // Create authors
        Author author1 = new Author("Aleksis Kivi", bio1);
        Author author2 = new Author("Mika Waltari", bio2);

        // Persist authors
        dao.saveAuthor(author1);
        dao.saveAuthor(author2);

        // Refresh authors to ensure they are managed
        author1 = dao.getAuthorById(author1.getId());
        author2 = dao.getAuthorById(author2.getId());

        // Set relationships
        bio1.setAuthor(author1);
        bio2.setAuthor(author2);

        // Create books
        PrintedBook printedBook = new PrintedBook("Sinuhe, egyptiläinen", 972);
        EBook eBook = new EBook("Seitsemän veljestä", "http://example.com/download");

        // Persist
        dao.saveBook(printedBook);
        dao.saveBook(eBook);

        // Create students
        Student student1 = new Student("Johanna Jokelainen");
        Student student2 = new Student("Iiro Elselä");

        // Persist
        dao.saveStudent(student1);
        dao.saveStudent(student2);

        // Create borrowed books
        BorrowedBook borrowedBook1 = new BorrowedBook(student1, printedBook, LocalDate.now());
        BorrowedBook borrowedBook2 = new BorrowedBook(student2, eBook, LocalDate.now());

        // Set relationships
        Set<BorrowedBook> borrowedBooks1 = new HashSet<>();
        borrowedBooks1.add(borrowedBook1);
        student1.setBorrowedBooks(borrowedBooks1);
        printedBook.setBorrowedBooks(borrowedBooks1);

        Set<BorrowedBook> borrowedBooks2 = new HashSet<>();
        borrowedBooks2.add(borrowedBook2);
        student2.setBorrowedBooks(borrowedBooks2);
        eBook.setBorrowedBooks(borrowedBooks2);

        // Persist
        dao.saveBorrowedBook(borrowedBook1);
        dao.saveBorrowedBook(borrowedBook2);

        // Print all borrowed books by student1
        System.out.println("Books borrowed by " + student1.getName() + ":");
        List<Book> booksBorrowedByStudent1 = dao.getBooksBorrowedByStudent(student1.getId());
        booksBorrowedByStudent1.forEach(book -> System.out.println(book.getTitle()));

        // Print all borrowed books by student2
        System.out.println("Books borrowed by " + student2.getName() + ":");
        List<Book> booksBorrowedByStudent2 = dao.getBooksBorrowedByStudent(student2.getId());
        booksBorrowedByStudent2.forEach(book -> System.out.println(book.getTitle()));

        dao.close();
    }

}
