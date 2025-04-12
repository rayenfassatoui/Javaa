package com.university.management.service;

import com.university.management.model.Grade;
import com.university.management.model.Student;
import com.university.management.model.Subject;

import java.util.List;
import java.util.Optional;

public interface GradeService {
    List<Grade> findAll();
    Optional<Grade> findById(Long id);
    Grade save(Grade grade);
    void deleteById(Long id);
    List<Grade> findByStudent(Student student);
    List<Grade> findBySubject(Subject subject);
    List<Grade> findByValueGreaterThanEqual(Double value);
    List<Grade> findByValueLessThanEqual(Double value);
    Optional<Grade> findByStudentAndSubject(Student student, Subject subject);
    Double calculateAverageByStudent(Student student);
    Double calculateAverageBySubject(Subject subject);
} 