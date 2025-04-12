package com.university.management.controller;

import com.university.management.model.Payment;
import com.university.management.model.Student;
import com.university.management.service.PaymentService;
import com.university.management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final StudentService studentService;

    @Autowired
    public PaymentController(PaymentService paymentService, StudentService studentService) {
        this.paymentService = paymentService;
        this.studentService = studentService;
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
        return "payments/form";
    }

    @PostMapping
    public String createPayment(@Valid @ModelAttribute Payment payment, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
            return "payments/form";
        }
        
        paymentService.savePayment(payment);
        redirectAttributes.addFlashAttribute("successMessage", "Payment has been recorded successfully!");
        return "redirect:/payments";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
            return "payments/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Payment not found!");
            return "redirect:/payments";
        }
    }

    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            return "payments/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Payment not found!");
            return "redirect:/payments";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            paymentService.deletePayment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Payment has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting payment: " + e.getMessage());
        }
        return "redirect:/payments";
    }

    @GetMapping("/student/{studentId}")
    public String getPaymentsByStudent(@PathVariable Long studentId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Student> student = studentService.findById(studentId);
        
        if (student.isPresent()) {
            List<Payment> payments = paymentService.getPaymentsByStudentId(studentId);
            model.addAttribute("payments", payments);
            model.addAttribute("student", student.get());
            model.addAttribute("totalAmount", paymentService.getTotalPaymentsByStudent(studentId));
            return "payments/student-payments";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found!");
            return "redirect:/payments";
        }
    }

    @GetMapping("/by-date-range")
    public String getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        List<Payment> payments = paymentService.getPaymentsByDateRange(startDateTime, endDateTime);
        model.addAttribute("payments", payments);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "payments/list";
    }

    @GetMapping("/by-method/{paymentMethod}")
    public String getPaymentsByMethod(@PathVariable String paymentMethod, Model model) {
        try {
            Payment.PaymentMethod method = Payment.PaymentMethod.valueOf(paymentMethod.toUpperCase());
            List<Payment> payments = paymentService.getPaymentsByMethod(method);
            model.addAttribute("payments", payments);
            model.addAttribute("paymentMethod", method);
            return "payments/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Invalid payment method: " + paymentMethod);
            return "redirect:/payments";
        }
    }
} 