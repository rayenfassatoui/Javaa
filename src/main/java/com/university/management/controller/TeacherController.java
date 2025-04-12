package com.university.management.controller;

import com.university.management.model.Teacher;
import com.university.management.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teachers/form";
    }

    @PostMapping
    public String createTeacher(@Valid @ModelAttribute Teacher teacher, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (teacher.getEmail() != null && teacherService.existsByEmail(teacher.getEmail())) {
            result.rejectValue("email", "error.teacher", "Teacher with this email already exists");
        }
        
        if (result.hasErrors()) {
            return "teachers/form";
        }
        
        teacherService.save(teacher);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher has been created successfully!");
        return "redirect:/teachers";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Teacher> teacher = teacherService.findById(id);
        
        if (teacher.isPresent()) {
            model.addAttribute("teacher", teacher.get());
            return "teachers/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Teacher not found!");
            return "redirect:/teachers";
        }
    }

    @GetMapping("/{id}")
    public String viewTeacher(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Teacher> teacher = teacherService.findById(id);
        
        if (teacher.isPresent()) {
            model.addAttribute("teacher", teacher.get());
            return "teachers/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Teacher not found!");
            return "redirect:/teachers";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Teacher has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting teacher: " + e.getMessage());
        }
        return "redirect:/teachers";
    }

    @GetMapping("/search")
    public String searchTeachers(@RequestParam String query, Model model) {
        model.addAttribute("teachers", teacherService.findByNameContaining(query));
        model.addAttribute("searchQuery", query);
        return "teachers/list";
    }

    @GetMapping("/department/{department}")
    public String getTeachersByDepartment(@PathVariable String department, Model model) {
        model.addAttribute("teachers", teacherService.findByDepartment(department));
        model.addAttribute("departmentName", department);
        return "teachers/list";
    }

    @GetMapping("/specialization/{specialization}")
    public String getTeachersBySpecialization(@PathVariable String specialization, Model model) {
        model.addAttribute("teachers", teacherService.findBySpecialization(specialization));
        model.addAttribute("specializationName", specialization);
        return "teachers/list";
    }
} 