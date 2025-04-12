package com.university.management.service;

import com.university.management.model.Class;
import com.university.management.model.Enrollment;
import com.university.management.model.Enrollment.EnrollmentStatus;
import com.university.management.model.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {

    List<Enrollment> findAll();
    
    Optional<Enrollment> findById(Long id);
    
    List<Enrollment> findByStudent(Student student);
    
    List<Enrollment> findByStudentId(Long studentId);
    
    List<Enrollment> findByClass(Class classEntity);
    
    List<Enrollment> findByClassId(Long classId);
    
    List<Enrollment> findByStatus(EnrollmentStatus status);
    
    List<Enrollment> findByEnrollmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    Optional<Enrollment> findByStudentAndClass(Student student, Class classEntity);
    
    Optional<Enrollment> findByStudentIdAndClassId(Long studentId, Long classId);
    
    Enrollment save(Enrollment enrollment);
    
    Enrollment enrollStudent(Long studentId, Long classId);
    
    Enrollment updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status);
    
    void deleteById(Long id);
    
    boolean isStudentEnrolledInClass(Long studentId, Long classId);
} 