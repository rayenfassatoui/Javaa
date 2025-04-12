package com.university.management.repository;

import com.university.management.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    
    List<Class> findByNameContainingIgnoreCase(String name);
    
    List<Class> findByDepartment(String department);
    
    List<Class> findByYear(Integer year);
    
    boolean existsByName(String name);
    
    long countByDepartment(String department);
} 