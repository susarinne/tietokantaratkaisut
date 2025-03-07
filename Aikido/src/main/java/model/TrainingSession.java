package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "training_sessions")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String location;
    private int duration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
     * An Instructor can conduct multiple TrainingSessions,
     * but a TrainingSession is led by a single Instructor
     * (Many-to-One).
     */
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    /*
     * A Student can attend multiple TrainingSessions,
     * and each session can have multiple students
     * (Many-to-Many via Attendance).
     */
    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    public TrainingSession() {
    }

    public TrainingSession(LocalDate date, String location, int duration, Instructor instructor) {
        this.date = date;
        this.location = location;
        this.duration = duration;
        this.instructor = instructor;
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

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
        attendance.setTrainingSession(this);
    }

    public void removeAttendance(Attendance attendance) {
        attendances.remove(attendance);
        attendance.setTrainingSession(null);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "id=" + id +
                ", instructor=" + instructor +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", duration=" + duration +
                ", attendances=" + (attendances != null ? attendances.size() : 0) +
                '}';
    }

}
