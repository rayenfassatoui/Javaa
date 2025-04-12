package com.university.management.repository;

import com.university.management.model.Class;
import com.university.management.model.Enrollment;
import com.university.management.model.Student;
import com.university.management.model.Enrollment.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    List<Enrollment> findByStudent(Student student);
    
    List<Enrollment> findByEnrolledClass(Class classEntity);
    
    List<Enrollment> findByStatus(EnrollmentStatus status);
    
    List<Enrollment> findByEnrollmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    Optional<Enrollment> findByStudentAndEnrolledClass(Student student, Class classEntity);
    
    List<Enrollment> findByStudentAndStatus(Student student, EnrollmentStatus status);
} 