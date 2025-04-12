package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", updatable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "reference_no", length = 50)
    private String referenceNumber;

    @Column(name = "description")
    private String description;

    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
    }
    
    // Explicit getter for amount to ensure compatibility
    public BigDecimal getAmount() {
        return amount;
    }

    public enum PaymentMethod {
        CASH, CREDIT_CARD, BANK_TRANSFER, CHECK
    }
}