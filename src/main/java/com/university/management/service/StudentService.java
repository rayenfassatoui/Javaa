package com.university.management.service;

import com.university.management.model.Student;
import com.university.management.model.Subject;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();
    
    Optional<Student> findById(Long id);
    
    Student save(Student student);
    
    void deleteById(Long id);
    
    List<Student> findByNameContaining(String name);
    
    List<Student> findByDepartment(String department);
    
    List<Student> findByEnrollmentYear(Integer year);
    
    List<Student> findBySubject(Subject subject);
    
    Optional<Student> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    long countByDepartment(String department);
} 