package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import converter.*;

@Entity
@Table(name = "attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AttendanceStatusConverter.class)
    private AttendanceStatus status;

    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "training_session_id")
    private TrainingSession trainingSession;

    public Attendance() {
    }

    public Attendance(Student student, TrainingSession trainingSession, AttendanceStatus status, String notes) {
        this.student = student;
        this.trainingSession = trainingSession;
        this.status = status;
        this.notes = notes;
    }

    /*
     * Implement @PrePersist and @PreUpdate to automatically
     * set timestamps when entities are created or updated.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", status=" + status +
                ", student=" + (student != null ? student.getName() : "none") +
                ", trainingSession=" + (trainingSession != null ? trainingSession.getDate() : "none") +
                ", notes='" + notes + '\'' +
                '}';
    }

}
