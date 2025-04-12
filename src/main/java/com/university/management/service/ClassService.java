package com.university.management.service;

import com.university.management.model.Class;

import java.util.List;
import java.util.Optional;

public interface ClassService {

    List<Class> findAll();
    
    Optional<Class> findById(Long id);
    
    Class save(Class classEntity);
    
    void deleteById(Long id);
    
    List<Class> findByNameContaining(String name);
    
    List<Class> findByYear(Integer year);
    
    List<Class> findByDepartment(String department);
    
    long countByDepartment(String department);
    
    boolean existsByName(String name);
    
    List<Class> searchClasses(String query);
    
    boolean isClassAvailable(Long id, Integer requiredCapacity);
} 