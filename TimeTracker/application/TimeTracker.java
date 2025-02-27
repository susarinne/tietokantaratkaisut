package application;

import dao.*;
import entity.*;

public class TimeTracker {

    public static void main(String[] args) {

        StudentDAO studentDao = new StudentDAO();

        Student student1 = new Student("Antti Aaltonen", "antti.aaltonen@metropolia.fi");
        studentDao.createStudent(student1);
        System.out.println("\nStudent saved: " + student1 + "\n");

        Student foundStudent = studentDao.findById(student1.getId());
        System.out.println("\nStudent details: " + foundStudent + "\n");

        foundStudent.setEmail("antti.aaltonen1@metropolia.fi");
        studentDao.updateStudent(foundStudent);
        System.out.println("\nUpdated details: " + foundStudent + "\n");

    }

}
