package com.university.management.repository;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByDepartment(String department);
    List<Teacher> findBySubjects(Subject subject);
    Optional<Teacher> findByEmail(String email);
    boolean existsByEmail(String email);
    
    List<Teacher> findBySpecialization(String specialization);
    
    List<Teacher> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Teacher> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
} 