package com.university.management.service.impl;

import com.university.management.model.Class;
import com.university.management.model.ClassSubject;
import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import com.university.management.repository.ClassRepository;
import com.university.management.repository.ClassSubjectRepository;
import com.university.management.repository.SubjectRepository;
import com.university.management.repository.TeacherRepository;
import com.university.management.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassSubjectServiceImpl implements ClassSubjectService {

    private final ClassSubjectRepository classSubjectRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public ClassSubjectServiceImpl(
            ClassSubjectRepository classSubjectRepository,
            ClassRepository classRepository,
            SubjectRepository subjectRepository,
            TeacherRepository teacherRepository) {
        this.classSubjectRepository = classSubjectRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getAllClassSubjects() {
        return classSubjectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassSubject> getClassSubjectById(Long id) {
        return classSubjectRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsByClass(Class classEntity) {
        return classSubjectRepository.findByClassEntity(classEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsByClassId(Long classId) {
        Optional<Class> classEntity = classRepository.findById(classId);
        return classEntity.map(classSubjectRepository::findByClassEntity).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsBySubject(Subject subject) {
        return classSubjectRepository.findBySubject(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsBySubjectId(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        return subject.map(classSubjectRepository::findBySubject).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsByTeacher(Teacher teacher) {
        return classSubjectRepository.findByTeacher(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSubject> getClassSubjectsByTeacherId(Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        return teacher.map(classSubjectRepository::findByTeacher).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassSubject> getClassSubjectByClassAndSubjectAndTeacher(
            Class classEntity, Subject subject, Teacher teacher) {
        return classSubjectRepository.findByClassEntityAndSubjectAndTeacher(classEntity, subject, teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassSubject> getClassSubjectByIds(Long classId, Long subjectId, Long teacherId) {
        Optional<Class> classEntity = classRepository.findById(classId);
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        
        if (classEntity.isPresent() && subject.isPresent() && teacher.isPresent()) {
            return classSubjectRepository.findByClassEntityAndSubjectAndTeacher(
                    classEntity.get(), subject.get(), teacher.get());
        }
        return Optional.empty();
    }

    @Override
    public ClassSubject saveClassSubject(ClassSubject classSubject) {
        return classSubjectRepository.save(classSubject);
    }

    @Override
    public ClassSubject assignSubjectToClass(Long classId, Long subjectId, Long teacherId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
        
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
        
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + teacherId));
        
        // Check if assignment already exists
        Optional<ClassSubject> existingAssignment = classSubjectRepository
                .findByClassEntityAndSubjectAndTeacher(classEntity, subject, teacher);
        
        if (existingAssignment.isPresent()) {
            return existingAssignment.get();
        }
        
        // Create new assignment
        ClassSubject classSubject = new ClassSubject();
        classSubject.setClassEntity(classEntity);
        classSubject.setSubject(subject);
        classSubject.setTeacher(teacher);
        
        return classSubjectRepository.save(classSubject);
    }

    @Override
    public void deleteClassSubject(Long id) {
        classSubjectRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByClassAndSubjectAndTeacher(Long classId, Long subjectId, Long teacherId) {
        return getClassSubjectByIds(classId, subjectId, teacherId).isPresent();
    }
} 