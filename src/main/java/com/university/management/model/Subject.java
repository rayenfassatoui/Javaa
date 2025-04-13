package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }

    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank(message = "Subject name is required")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Explicit getter for name to ensure compatibility
    public String getName() {
        return name;
    }

    // Explicit setter for name to ensure compatibility
    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "Subject code is required")
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    // Explicit getter for code to ensure compatibility
    public String getCode() {
        return code;
    }

    // Explicit setter for code to ensure compatibility
    public void setCode(String code) {
        this.code = code;
    }

    @Min(value = 1, message = "Credit hours must be at least 1")
    @Column(name = "credit_hours", nullable = false)
    private Integer creditHours;

    // Explicit getter for creditHours to ensure compatibility
    public Integer getCreditHours() {
        return creditHours;
    }

    // Explicit setter for creditHours to ensure compatibility
    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }

    @Column(name = "description")
    private String description;

    // Explicit getter for description to ensure compatibility
    public String getDescription() {
        return description;
    }

    // Explicit setter for description to ensure compatibility
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name = "department", length = 50)
    private String department;

    // Explicit getter for department to ensure compatibility
    public String getDepartment() {
        return department;
    }

    // Explicit setter for department to ensure compatibility
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers = new HashSet<>();

    // Explicit getter for teachers to ensure compatibility
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    // Explicit setter for teachers to ensure compatibility
    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();
    
    // Explicit getter for students to ensure compatibility
    public Set<Student> getStudents() {
        return students;
    }
    
    // Explicit setter for students to ensure compatibility
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}