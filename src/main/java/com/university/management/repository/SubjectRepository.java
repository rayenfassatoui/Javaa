package com.university.management.repository;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    List<Subject> findByNameContainingIgnoreCase(String name);
    
    List<Subject> findByDepartment(String department);
    
    Optional<Subject> findByCode(String code);
    
    boolean existsByCode(String code);
    
    List<Subject> findByCredits(Integer credits);
    
    List<Subject> findByCreditHours(Integer creditHours);
    
    List<Subject> findByTeachers(Teacher teacher);
} 