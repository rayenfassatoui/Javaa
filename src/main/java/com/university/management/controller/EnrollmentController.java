package com.university.management.controller;

import com.university.management.model.Class;
import com.university.management.model.Enrollment;
import com.university.management.model.Enrollment.EnrollmentStatus;
import com.university.management.model.Student;
import com.university.management.service.ClassService;
import com.university.management.service.EnrollmentService;
import com.university.management.service.StudentService;
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
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final ClassService classService;

    @Autowired
    public EnrollmentController(
            EnrollmentService enrollmentService,
            StudentService studentService,
            ClassService classService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.classService = classService;
    }

    @GetMapping
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollments/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("classes", classService.findAll());
        model.addAttribute("statuses", EnrollmentStatus.values());
        return "enrollments/form";
    }

    @PostMapping
    public String createEnrollment(@Valid @ModelAttribute Enrollment enrollment, 
                                 BindingResult result, 
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // Check if student is already enrolled in the class
        if (enrollment.getStudent() != null && enrollment.getEnrolledClass() != null &&
            enrollmentService.isStudentEnrolledInClass(
                enrollment.getStudent().getId(), enrollment.getEnrolledClass().getId()) &&
            (enrollment.getId() == null)) { // Only check for new enrollments
            
            result.rejectValue("student", "error.enrollment", "Student is already enrolled in this class");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("classes", classService.findAll());
            model.addAttribute("statuses", EnrollmentStatus.values());
            return "enrollments/form";
        }
        
        enrollmentService.save(enrollment);
        redirectAttributes.addFlashAttribute("successMessage", "Enrollment has been created successfully!");
        return "redirect:/enrollments";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Enrollment> enrollment = enrollmentService.findById(id);
        
        if (enrollment.isPresent()) {
            model.addAttribute("enrollment", enrollment.get());
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("classes", classService.findAll());
            model.addAttribute("statuses", EnrollmentStatus.values());
            return "enrollments/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Enrollment not found!");
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/{id}")
    public String viewEnrollment(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Enrollment> enrollment = enrollmentService.findById(id);
        
        if (enrollment.isPresent()) {
            model.addAttribute("enrollment", enrollment.get());
            return "enrollments/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Enrollment not found!");
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteEnrollment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Enrollment has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting enrollment: " + e.getMessage());
        }
        return "redirect:/enrollments";
    }

    @GetMapping("/{id}/update-status/{status}")
    public String updateEnrollmentStatus(
            @PathVariable Long id, 
            @PathVariable String status, 
            RedirectAttributes redirectAttributes) {
        try {
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            enrollmentService.updateEnrollmentStatus(id, enrollmentStatus);
            redirectAttributes.addFlashAttribute("successMessage", 
                    "Enrollment status has been updated to " + enrollmentStatus);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid status: " + status);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating enrollment: " + e.getMessage());
        }
        return "redirect:/enrollments/" + id;
    }

    @GetMapping("/student/{studentId}")
    public String getEnrollmentsByStudent(@PathVariable Long studentId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.findById(studentId);
        
        if (student.isPresent()) {
            List<Enrollment> enrollments = enrollmentService.findByStudentId(studentId);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("student", student.get());
            return "enrollments/student-enrollments";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/class/{classId}")
    public String getEnrollmentsByClass(@PathVariable Long classId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Class> classEntity = classService.findById(classId);
        
        if (classEntity.isPresent()) {
            List<Enrollment> enrollments = enrollmentService.findByClassId(classId);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("classEntity", classEntity.get());
            return "enrollments/class-enrollments";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class not found!");
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/status/{status}")
    public String getEnrollmentsByStatus(@PathVariable String status, Model model, RedirectAttributes redirectAttributes) {
        try {
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            List<Enrollment> enrollments = enrollmentService.findByStatus(enrollmentStatus);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("status", enrollmentStatus);
            return "enrollments/list";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid status: " + status);
            return "redirect:/enrollments";
        }
    }

    @GetMapping("/enroll-student")
    public String showEnrollStudentForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("classes", classService.findAll());
        return "enrollments/enroll-student";
    }

    @PostMapping("/enroll-student")
    public String enrollStudent(
            @RequestParam Long studentId, 
            @RequestParam Long classId,
            RedirectAttributes redirectAttributes) {
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, classId);
            redirectAttributes.addFlashAttribute("successMessage", "Student has been enrolled successfully!");
            return "redirect:/enrollments/" + enrollment.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error enrolling student: " + e.getMessage());
            return "redirect:/enrollments/enroll-student";
        }
    }
} 