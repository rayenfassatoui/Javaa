package com.university.management.service.impl;

import com.university.management.model.Class;
import com.university.management.model.Enrollment;
import com.university.management.model.Enrollment.EnrollmentStatus;
import com.university.management.model.Student;
import com.university.management.repository.ClassRepository;
import com.university.management.repository.EnrollmentRepository;
import com.university.management.repository.StudentRepository;
import com.university.management.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    @Autowired
    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            ClassRepository classRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrollment> findById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByStudent(Student student) {
        return enrollmentRepository.findByStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByStudentId(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.map(enrollmentRepository::findByStudent).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByClass(Class classEntity) {
        return enrollmentRepository.findByEnrolledClass(classEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByClassId(Long classId) {
        Optional<Class> classEntity = classRepository.findById(classId);
        return classEntity.map(enrollmentRepository::findByEnrolledClass).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByStatus(EnrollmentStatus status) {
        return enrollmentRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findByEnrollmentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return enrollmentRepository.findByEnrollmentDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrollment> findByStudentAndClass(Student student, Class classEntity) {
        return enrollmentRepository.findByStudentAndEnrolledClass(student, classEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrollment> findByStudentIdAndClassId(Long studentId, Long classId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Class> classEntity = classRepository.findById(classId);
        
        if (student.isPresent() && classEntity.isPresent()) {
            return enrollmentRepository.findByStudentAndEnrolledClass(student.get(), classEntity.get());
        }
        return Optional.empty();
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment enrollStudent(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));
        
        // Check if student is already enrolled
        Optional<Enrollment> existingEnrollment = enrollmentRepository
                .findByStudentAndEnrolledClass(student, classEntity);
        
        if (existingEnrollment.isPresent()) {
            return existingEnrollment.get();
        }
        
        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setEnrolledClass(classEntity);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId));
        
        enrollment.setStatus(status);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteById(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isStudentEnrolledInClass(Long studentId, Long classId) {
        return findByStudentIdAndClassId(studentId, classId).isPresent();
    }
} 