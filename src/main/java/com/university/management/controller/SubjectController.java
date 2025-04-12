package com.university.management.controller;

import com.university.management.model.Subject;
import com.university.management.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subjects/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects/form";
    }

    @PostMapping
    public String createSubject(@Valid @ModelAttribute Subject subject, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (subject.getCode() != null && subjectService.existsByCode(subject.getCode())) {
            result.rejectValue("code", "error.subject", "Subject with this code already exists");
        }
        
        if (result.hasErrors()) {
            return "subjects/form";
        }
        
        subjectService.save(subject);
        redirectAttributes.addFlashAttribute("successMessage", "Subject has been created successfully!");
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Subject> subject = subjectService.findById(id);
        
        if (subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            return "subjects/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Subject not found!");
            return "redirect:/subjects";
        }
    }

    @GetMapping("/{id}")
    public String viewSubject(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Subject> subject = subjectService.findById(id);
        
        if (subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            return "subjects/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Subject not found!");
            return "redirect:/subjects";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            subjectService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Subject has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting subject: " + e.getMessage());
        }
        return "redirect:/subjects";
    }

    @GetMapping("/search")
    public String searchSubjects(@RequestParam String query, Model model) {
        model.addAttribute("subjects", subjectService.searchSubjects(query));
        model.addAttribute("searchQuery", query);
        return "subjects/list";
    }

    @GetMapping("/creditHours/{creditHours}")
    public String getSubjectsByCreditHours(@PathVariable Integer creditHours, Model model) {
        model.addAttribute("subjects", subjectService.findByCredits(creditHours));
        model.addAttribute("creditHours", creditHours);
        return "subjects/list";
    }
} 