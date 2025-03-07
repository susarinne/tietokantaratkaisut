# Aikido Training Session Tracker

A JPA/Hibernate-based backend system for tracking Aikido training sessions, instructors, students, and performance progress.

## Overview

This application demonstrates advanced JPA features including:

-   Entity relationships (One-to-Many, Many-to-One, Many-to-Many)
-   Entity lifecycle events (@PrePersist, @PreUpdate, @PostLoad)
-   JPA converters for custom data types
-   Optimistic locking for concurrent data modification
-   JPQL and Criteria API queries

## Project Structure

-   src/main/java/model/: Entity classes
-   src/main/java/dao/: Data access objects
-   src/main/java/converter/: Custom JPA converters
-   src/main/java/Main.java: Main application with JPQL and Criteria API examples

### Relationships

-   Student to TrainingSession: Many-to-Many via Attendance join entity
-   Instructor to TrainingSession: Many-to-One relationship
-   Student to ProgressReport: One-to-Many relationship

### JPQL Queries

-   Retrieve all training sessions attended by a specific student
-   List all students who have a specific rank
-   Get all instructors specialized in a particular Aikido technique
-   Fetch students with progress reports in the last three months

### Criteria API Queries

-   Find all students who joined within the last six months
-   Search for training sessions held in a specific location
-   Retrieve all instructors with more than five years of experience

## Setup Instructions

### Prerequisites

-   Java 21
-   Maven
-   MySQL 8.0+

### Database Setup

1. Create a MySQL database named `tietokantaratkaisut`:

    ```sql
    CREATE DATABASE tietokantaratkaisut;
    ```

2. Configure database connection:

-   Set environment variables DB_USER and DB_PASSWORD with your MySQL credentials
-   Or modify persistence.xml to include your credentials directly

### Building and Running

1. Build the project:

    ```bash
    mvn clean compile
    ```

2. Run the application:

    ```bash
    mvn exec:java -Dexec.mainClass="Main"
    ```
