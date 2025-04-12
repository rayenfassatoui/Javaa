package com.university.management.controller;

import com.university.management.model.Class;
import com.university.management.model.ClassSubject;
import com.university.management.model.Subject;
import com.university.management.model.Teacher;
import com.university.management.service.ClassService;
import com.university.management.service.ClassSubjectService;
import com.university.management.service.SubjectService;
import com.university.management.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/class-subjects")
public class ClassSubjectController {

    private final ClassSubjectService classSubjectService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Autowired
    public ClassSubjectController(
            ClassSubjectService classSubjectService,
            ClassService classService,
            SubjectService subjectService,
            TeacherService teacherService) {
        this.classSubjectService = classSubjectService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String listClassSubjects(Model model) {
        model.addAttribute("classSubjects", classSubjectService.getAllClassSubjects());
        return "class-subjects/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("classSubject", new ClassSubject());
        model.addAttribute("classes", classService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "class-subjects/form";
    }

    @PostMapping
    public String createClassSubject(@Valid @ModelAttribute ClassSubject classSubject, 
                                   BindingResult result, 
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        // Check if assignment already exists
        if (classSubject.getClassEntity() != null && classSubject.getSubject() != null && classSubject.getTeacher() != null &&
            classSubjectService.existsByClassAndSubjectAndTeacher(
                classSubject.getClassEntity().getId(), 
                classSubject.getSubject().getId(), 
                classSubject.getTeacher().getId()) &&
            (classSubject.getId() == null)) { // Only check for new assignments
            
            result.rejectValue("subject", "error.classSubject", "This subject is already assigned to this class with this teacher");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("classes", classService.findAll());
            model.addAttribute("subjects", subjectService.findAll());
            model.addAttribute("teachers", teacherService.findAll());
            return "class-subjects/form";
        }
        
        classSubjectService.saveClassSubject(classSubject);
        redirectAttributes.addFlashAttribute("successMessage", "Subject has been assigned to class successfully!");
        return "redirect:/class-subjects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ClassSubject> classSubject = classSubjectService.getClassSubjectById(id);
        
        if (classSubject.isPresent()) {
            model.addAttribute("classSubject", classSubject.get());
            model.addAttribute("classes", classService.findAll());
            model.addAttribute("subjects", subjectService.findAll());
            model.addAttribute("teachers", teacherService.findAll());
            return "class-subjects/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class-Subject assignment not found!");
            return "redirect:/class-subjects";
        }
    }

    @GetMapping("/{id}")
    public String viewClassSubject(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ClassSubject> classSubject = classSubjectService.getClassSubjectById(id);
        
        if (classSubject.isPresent()) {
            model.addAttribute("classSubject", classSubject.get());
            return "class-subjects/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class-Subject assignment not found!");
            return "redirect:/class-subjects";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteClassSubject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classSubjectService.deleteClassSubject(id);
            redirectAttributes.addFlashAttribute("successMessage", "Class-Subject assignment has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting assignment: " + e.getMessage());
        }
        return "redirect:/class-subjects";
    }

    @GetMapping("/class/{classId}")
    public String getClassSubjectsByClass(@PathVariable Long classId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Class> classEntity = classService.findById(classId);
        
        if (classEntity.isPresent()) {
            List<ClassSubject> classSubjects = classSubjectService.getClassSubjectsByClassId(classId);
            model.addAttribute("classSubjects", classSubjects);
            model.addAttribute("classEntity", classEntity.get());
            return "class-subjects/class-subjects";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class not found!");
            return "redirect:/class-subjects";
        }
    }

    @GetMapping("/subject/{subjectId}")
    public String getClassSubjectsBySubject(@PathVariable Long subjectId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Subject> subject = subjectService.findById(subjectId);
        
        if (subject.isPresent()) {
            List<ClassSubject> classSubjects = classSubjectService.getClassSubjectsBySubjectId(subjectId);
            model.addAttribute("classSubjects", classSubjects);
            model.addAttribute("subject", subject.get());
            return "class-subjects/subject-classes";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Subject not found!");
            return "redirect:/class-subjects";
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public String getClassSubjectsByTeacher(@PathVariable Long teacherId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Teacher> teacher = teacherService.findById(teacherId);
        
        if (teacher.isPresent()) {
            List<ClassSubject> classSubjects = classSubjectService.getClassSubjectsByTeacherId(teacherId);
            model.addAttribute("classSubjects", classSubjects);
            model.addAttribute("teacher", teacher.get());
            return "class-subjects/teacher-classes";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Teacher not found!");
            return "redirect:/class-subjects";
        }
    }

    @GetMapping("/assign-subject")
    public String showAssignSubjectForm(Model model) {
        model.addAttribute("classes", classService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        return "class-subjects/assign-subject";
    }

    @PostMapping("/assign-subject")
    public String assignSubject(
            @RequestParam Long classId, 
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            RedirectAttributes redirectAttributes) {
        try {
            ClassSubject classSubject = classSubjectService.assignSubjectToClass(classId, subjectId, teacherId);
            redirectAttributes.addFlashAttribute("successMessage", "Subject has been assigned to class successfully!");
            return "redirect:/class-subjects/" + classSubject.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error assigning subject: " + e.getMessage());
            return "redirect:/class-subjects/assign-subject";
        }
    }
} 