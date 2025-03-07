package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import converter.*;

import java.util.HashSet;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Convert(converter = AikidoRankConverter.class)
    private AikidoRank rank;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private int membershipDuration;

    /*
     * A Student can attend multiple TrainingSessions,
     * and each session can have multiple students
     * (Many-to-Many via Attendance).
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    /*
     * Each Student can have multiple ProgressReports,
     * but each report is linked to a single Student
     * (One-to-Many).
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgressReport> progressReports = new ArrayList<>();

    public Student() {
    }

    public Student(String name, String email, AikidoRank rank, LocalDate joinDate) {
        this.name = name;
        this.email = email;
        this.rank = rank;
        this.joinDate = joinDate;
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

    @PostLoad
    protected void calculateMembershipDuration() {
        this.membershipDuration = LocalDate.now().getYear() - joinDate.getYear();
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
        attendance.setStudent(this);
    }

    public void removeAttendance(Attendance attendance) {
        attendances.remove(attendance);
        attendance.setStudent(null);
    }

    public void addProgressReport(ProgressReport progressReport) {
        progressReports.add(progressReport);
        progressReport.setStudent(this);
    }

    public void removeProgressReport(ProgressReport progressReport) {
        progressReports.remove(progressReport);
        progressReport.setStudent(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AikidoRank getRank() {
        return rank;
    }

    public void setRank(AikidoRank rank) {
        this.rank = rank;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public int getMembershipDuration() {
        return membershipDuration;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public List<ProgressReport> getProgressReports() {
        return progressReports;
    }

    public void setProgressReports(List<ProgressReport> progressReports) {
        this.progressReports = progressReports;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", rank='" + rank + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }

}
