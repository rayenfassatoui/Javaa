package com.university.management.controller;

import com.university.management.model.Class;
import com.university.management.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/classes")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public String listClasses(Model model) {
        model.addAttribute("classes", classService.findAll());
        return "classes/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("classEntity", new Class());
        return "classes/form";
    }

    @PostMapping
    public String createClass(@Valid @ModelAttribute("classEntity") Class classEntity, 
                             BindingResult result, 
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "classes/form";
        }
        
        classService.save(classEntity);
        redirectAttributes.addFlashAttribute("successMessage", "Class has been created successfully!");
        return "redirect:/classes";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Class> classOptional = classService.findById(id);
        
        if (classOptional.isPresent()) {
            model.addAttribute("classEntity", classOptional.get());
            return "classes/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class not found!");
            return "redirect:/classes";
        }
    }

    @GetMapping("/{id}")
    public String viewClass(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Class> classOptional = classService.findById(id);
        
        if (classOptional.isPresent()) {
            model.addAttribute("classEntity", classOptional.get());
            return "classes/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class not found!");
            return "redirect:/classes";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteClass(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Class has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting class: " + e.getMessage());
        }
        return "redirect:/classes";
    }

    @GetMapping("/search")
    public String searchClasses(@RequestParam String query, Model model) {
        model.addAttribute("classes", classService.searchClasses(query));
        model.addAttribute("searchQuery", query);
        return "classes/list";
    }

    @GetMapping("/department/{department}")
    public String getClassesByDepartment(@PathVariable String department, Model model) {
        model.addAttribute("classes", classService.findByDepartment(department));
        model.addAttribute("departmentName", department);
        return "classes/list";
    }
} 