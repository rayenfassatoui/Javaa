package com.university.management.service.impl;

import com.university.management.model.Class;
import com.university.management.repository.ClassRepository;
import com.university.management.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findAll() {
        return classRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Class> findById(Long id) {
        return classRepository.findById(id);
    }

    @Override
    public Class save(Class classEntity) {
        return classRepository.save(classEntity);
    }

    @Override
    public void deleteById(Long id) {
        classRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findByNameContaining(String name) {
        return classRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findByYear(Integer year) {
        return classRepository.findByYear(year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> findByDepartment(String department) {
        return classRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDepartment(String department) {
        return classRepository.countByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return classRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Class> searchClasses(String query) {
        return classRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isClassAvailable(Long id, Integer requiredCapacity) {
        Optional<Class> classOptional = classRepository.findById(id);
        return classOptional.isPresent() && classOptional.get().getCapacity() >= requiredCapacity;
    }
} 