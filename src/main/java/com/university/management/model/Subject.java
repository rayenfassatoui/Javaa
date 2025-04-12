package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @NotBlank(message = "Subject name is required")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Subject code is required")
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @Min(value = 1, message = "Credit hours must be at least 1")
    @Column(name = "credit_hours", nullable = false)
    private Integer creditHours;

    @Column(name = "description")
    private String description;
} 