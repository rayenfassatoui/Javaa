package com.university.management.service;

import com.university.management.model.Class;
import com.university.management.model.ClassSubject;
import com.university.management.model.Subject;
import com.university.management.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface ClassSubjectService {

    List<ClassSubject> getAllClassSubjects();
    
    Optional<ClassSubject> getClassSubjectById(Long id);
    
    List<ClassSubject> getClassSubjectsByClass(Class classEntity);
    
    List<ClassSubject> getClassSubjectsByClassId(Long classId);
    
    List<ClassSubject> getClassSubjectsBySubject(Subject subject);
    
    List<ClassSubject> getClassSubjectsBySubjectId(Long subjectId);
    
    List<ClassSubject> getClassSubjectsByTeacher(Teacher teacher);
    
    List<ClassSubject> getClassSubjectsByTeacherId(Long teacherId);
    
    Optional<ClassSubject> getClassSubjectByClassAndSubjectAndTeacher(
            Class classEntity, Subject subject, Teacher teacher);
    
    Optional<ClassSubject> getClassSubjectByIds(Long classId, Long subjectId, Long teacherId);
    
    ClassSubject saveClassSubject(ClassSubject classSubject);
    
    ClassSubject assignSubjectToClass(Long classId, Long subjectId, Long teacherId);
    
    void deleteClassSubject(Long id);
    
    boolean existsByClassAndSubjectAndTeacher(Long classId, Long subjectId, Long teacherId);
} 