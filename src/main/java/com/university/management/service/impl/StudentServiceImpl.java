package com.university.management.service.impl;

import com.university.management.model.Student;
import com.university.management.model.Subject;
import com.university.management.repository.StudentRepository;
import com.university.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByNameContaining(String name) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByEnrollmentYear(Integer year) {
        return studentRepository.findByEnrollmentYear(year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findBySubject(Subject subject) {
        return studentRepository.findBySubjects(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDepartment(String department) {
        return studentRepository.countByDepartment(department);
    }
} 