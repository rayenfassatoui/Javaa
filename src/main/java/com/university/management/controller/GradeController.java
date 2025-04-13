package com.university.management.controller;

import com.university.management.model.Grade;
import com.university.management.model.Student;
import com.university.management.service.GradeService;
import com.university.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    public String viewStudentGrades(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        List<Grade> grades = gradeService.findByStudent(student);
        
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        
        return "grades/student";
    }
}