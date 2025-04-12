package com.university.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }

    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
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
    @JoinColumn(name = "class_id", nullable = false)
    private Class enrolledClass;

    // Explicit getter for enrolledClass to ensure compatibility
    public Class getEnrolledClass() {
        return enrolledClass;
    }

    // Explicit setter for enrolledClass to ensure compatibility
    public void setEnrolledClass(Class enrolledClass) {
        this.enrolledClass = enrolledClass;
    }

    @Column(name = "enrollment_date", updatable = false)
    private LocalDateTime enrollmentDate;

    // Explicit getter for enrollmentDate to ensure compatibility
    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    // Explicit setter for enrollmentDate to ensure compatibility
    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    // Explicit getter for status to ensure compatibility
    public EnrollmentStatus getStatus() {
        return status;
    }

    // Explicit setter for status to ensure compatibility
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDateTime.now();
    }

    public enum EnrollmentStatus {
        ACTIVE, COMPLETED, WITHDRAWN, SUSPENDED
    }
}