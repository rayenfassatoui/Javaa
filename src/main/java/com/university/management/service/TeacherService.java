package com.university.management.service;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> findAll();
    
    Optional<Teacher> findById(Long id);
    
    Teacher save(Teacher teacher);
    
    void deleteById(Long id);
    
    List<Teacher> findByNameContaining(String name);
    
    List<Teacher> findByDepartment(String department);
    
    List<Teacher> findBySpecialization(String specialization);
    
    List<Teacher> findBySubject(Subject subject);
    
    Optional<Teacher> findByEmail(String email);
    
    boolean existsByEmail(String email);
} 