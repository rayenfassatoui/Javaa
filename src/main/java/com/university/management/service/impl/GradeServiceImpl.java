package com.university.management.service.impl;

import com.university.management.model.Grade;
import com.university.management.model.Student;
import com.university.management.model.Subject;
import com.university.management.repository.GradeRepository;
import com.university.management.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    @Override
    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findByStudent(Student student) {
        return gradeRepository.findByStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findBySubject(Subject subject) {
        return gradeRepository.findBySubject(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findByValueGreaterThanEqual(Double value) {
        return gradeRepository.findByValueGreaterThanEqual(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Grade> findByValueLessThanEqual(Double value) {
        return gradeRepository.findByValueLessThanEqual(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Grade> findByStudentAndSubject(Student student, Subject subject) {
        return gradeRepository.findByStudentAndSubject(student, subject);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageByStudent(Student student) {
        List<Grade> grades = gradeRepository.findByStudent(student);
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream()
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageBySubject(Subject subject) {
        List<Grade> grades = gradeRepository.findBySubject(subject);
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream()
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);
    }
} 