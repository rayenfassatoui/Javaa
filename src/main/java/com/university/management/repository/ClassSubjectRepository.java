package com.university.management.repository;

import com.university.management.model.Class;
import com.university.management.model.ClassSubject;
import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Long> {
    
    List<ClassSubject> findByClassEntity(Class classEntity);
    
    List<ClassSubject> findBySubject(Subject subject);
    
    List<ClassSubject> findByTeacher(Teacher teacher);
    
    Optional<ClassSubject> findByClassEntityAndSubjectAndTeacher(Class classEntity, Subject subject, Teacher teacher);
} 