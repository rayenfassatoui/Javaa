package com.university.management.service.impl;

import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import com.university.management.repository.SubjectRepository;
import com.university.management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByNameContaining(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByDepartment(String department) {
        return subjectRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subject> findByCode(String code) {
        return subjectRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return subjectRepository.existsByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> searchSubjects(String query) {
        return subjectRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getSubjectsByCreditHours(Integer creditHours) {
        return subjectRepository.findByCreditHours(creditHours);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByCredits(Integer credits) {
        return subjectRepository.findByCreditHours(credits);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findByTeachers(Teacher teacher) {
        return subjectRepository.findByTeachers(teacher);
    }

    @Override
    public boolean isSubjectInUse(Long id) {
        // Check if this subject is assigned to any class
        // This would typically involve checking ClassSubject repository
        // For now, we'll return false as a placeholder
        return false;
    }
} 