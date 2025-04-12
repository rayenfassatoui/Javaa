package com.university.management.service;

import com.university.management.model.Payment;
import com.university.management.model.Payment.PaymentMethod;
import com.university.management.model.Student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> getAllPayments();
    
    Optional<Payment> getPaymentById(Long id);
    
    List<Payment> getPaymentsByStudent(Student student);
    
    List<Payment> getPaymentsByStudentId(Long studentId);
    
    List<Payment> getPaymentsByMethod(PaymentMethod paymentMethod);
    
    List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Payment> getPaymentsByAmountGreaterThan(BigDecimal amount);
    
    Payment savePayment(Payment payment);
    
    void deletePayment(Long id);
    
    BigDecimal getTotalPaymentsByStudent(Long studentId);
} 