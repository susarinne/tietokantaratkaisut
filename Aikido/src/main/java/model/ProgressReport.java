package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_reports")
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column
    private String achievements;

    @Column(name = "areas_for_improvement", length = 1000)
    private String areasForImprovement;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
     * Use @Version for optimistic locking to handle
     * concurrent updates to student progress reports.
     */
    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public ProgressReport() {
    }

    public ProgressReport(Student student, LocalDate reportDate, String achievements, String areasForImprovement) {
        this.student = student;
        this.reportDate = reportDate;
        this.achievements = achievements;
        this.areasForImprovement = areasForImprovement;
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getAreasForImprovement() {
        return areasForImprovement;
    }

    public void setAreasForImprovement(String areasForImprovement) {
        this.areasForImprovement = areasForImprovement;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ProgressReport{" +
                "id=" + id +
                ", student=" + student +
                ", reportDate=" + reportDate +
                ", achievements='" + achievements + '\'' +
                ", areasForImprovement='" + areasForImprovement + '\'' +
                '}';
    }

}
