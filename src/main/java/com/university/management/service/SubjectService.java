package com.university.management.service;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    List<Subject> findAll();
    
    Optional<Subject> findById(Long id);
    
    Subject save(Subject subject);
    
    void deleteById(Long id);

    @Transactional(readOnly = true)
    List<Subject> findByNameContaining(String name);

    @Transactional(readOnly = true)
    List<Subject> findByDepartment(String department);

    @Transactional(readOnly = true)
    Optional<Subject> findByCode(String code);

    boolean existsByCode(String code);

    @Transactional(readOnly = true)
    List<Subject> getSubjectsByCreditHours(Integer creditHours);

    List<Subject> findByCredits(Integer credits);
    
    List<Subject> searchSubjects(String query);

    @Transactional(readOnly = true)
    List<Subject> findByTeachers(Teacher teacher);

    boolean isSubjectInUse(Long id);
}