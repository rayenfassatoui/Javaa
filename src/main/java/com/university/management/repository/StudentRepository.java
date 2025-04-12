package com.university.management.repository;

import com.university.management.model.Student;
import com.university.management.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByDepartment(String department);
    List<Student> findBySubjects(Subject subject);
    boolean existsByEmail(String email);
    long countByDepartment(String department);
    List<Student> findByEnrollmentYear(Integer year);
} 