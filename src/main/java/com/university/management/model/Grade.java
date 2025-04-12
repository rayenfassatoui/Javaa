package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "grades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "subject_id"})
})
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }
    
    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }
    
    @NotNull
    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private Double value;
    
    // Explicit getter for value to ensure compatibility
    public Double getValue() {
        return value;
    }
    
    // Explicit setter for value to ensure compatibility
    public void setValue(Double value) {
        this.value = value;
    }
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    // Explicit getter for student to ensure compatibility
    public Student getStudent() {
        return student;
    }
    
    // Explicit setter for student to ensure compatibility
    public void setStudent(Student student) {
        this.student = student;
    }
    
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    // Explicit getter for subject to ensure compatibility
    public Subject getSubject() {
        return subject;
    }
    
    // Explicit setter for subject to ensure compatibility
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    @Column(name = "grade_date")
    private LocalDateTime gradeDate;
    
    // Explicit getter for gradeDate to ensure compatibility
    public LocalDateTime getGradeDate() {
        return gradeDate;
    }
    
    // Explicit setter for gradeDate to ensure compatibility
    public void setGradeDate(LocalDateTime gradeDate) {
        this.gradeDate = gradeDate;
    }
    
    private String comments;
    
    // Explicit getter for comments to ensure compatibility
    public String getComments() {
        return comments;
    }
    
    // Explicit setter for comments to ensure compatibility
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Explicit getter for createdAt to ensure compatibility
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Explicit setter for createdAt to ensure compatibility
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Explicit getter for updatedAt to ensure compatibility
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Explicit setter for updatedAt to ensure compatibility
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.gradeDate == null) {
            this.gradeDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}