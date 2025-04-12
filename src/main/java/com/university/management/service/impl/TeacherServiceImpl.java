package com.university.management.service.impl;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import com.university.management.repository.TeacherRepository;
import com.university.management.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findByNameContaining(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findByDepartment(String department) {
        return teacherRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findBySpecialization(String specialization) {
        return teacherRepository.findBySpecialization(specialization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findBySubject(Subject subject) {
        return teacherRepository.findBySubjects(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }
} 