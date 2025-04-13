package com.university.management.repository;

import com.university.management.model.Payment;
import com.university.management.model.Payment.PaymentMethod;
import com.university.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudent(Student student);
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Payment> findByAmountGreaterThan(BigDecimal amount);
    List<Payment> findByAmountGreaterThanEqual(Double amount);
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
}