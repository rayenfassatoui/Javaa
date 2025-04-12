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
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @NotBlank(message = "Class name is required")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "room", length = 20)
    private String room;

    @Column(name = "department", length = 50)
    private String department;
    
    @Column(name = "year")
    private Integer year;
} 