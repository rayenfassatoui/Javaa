package com.university.management.controller;

import com.university.management.model.Student;
import com.university.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute Student student, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (studentService.existsByEmail(student.getEmail())) {
            result.rejectValue("email", "error.student", "Student with this email already exists");
        }
        
        if (result.hasErrors()) {
            return "students/form";
        }
        
        studentService.save(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student has been created successfully!");
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/students";
        }
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.findById(id);
        
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/students";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting student: " + e.getMessage());
        }
        return "redirect:/students";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam String query, Model model) {
        model.addAttribute("students", studentService.findByNameContaining(query));
        model.addAttribute("searchQuery", query);
        return "students/list";
    }
} 