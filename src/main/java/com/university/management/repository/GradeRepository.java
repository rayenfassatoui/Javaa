package com.university.management.repository;

import com.university.management.model.Grade;
import com.university.management.model.Student;
import com.university.management.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(Student student);
    List<Grade> findBySubject(Subject subject);
    List<Grade> findByValueGreaterThanEqual(Double value);
    List<Grade> findByValueLessThanEqual(Double value);
    Optional<Grade> findByStudentAndSubject(Student student, Subject subject);
} 