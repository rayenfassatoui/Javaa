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
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "specialization", length = 100)
    private String specialization;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "teacher_subject",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();
    
    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }
    
    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }
    
    // Explicit getter for firstName to ensure compatibility
    public String getFirstName() {
        return firstName;
    }
    
    // Explicit setter for firstName to ensure compatibility
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    // Explicit getter for lastName to ensure compatibility
    public String getLastName() {
        return lastName;
    }
    
    // Explicit setter for lastName to ensure compatibility
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // Explicit getter for email to ensure compatibility
    public String getEmail() {
        return email;
    }
    
    // Explicit setter for email to ensure compatibility
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Explicit getter for department to ensure compatibility
    public String getDepartment() {
        return department;
    }
    
    // Explicit setter for department to ensure compatibility
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Explicit getter for specialization to ensure compatibility
    public String getSpecialization() {
        return specialization;
    }
    
    // Explicit setter for specialization to ensure compatibility
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    // Explicit getter for subjects to ensure compatibility
    public Set<Subject> getSubjects() {
        return subjects;
    }
    
    // Explicit setter for subjects to ensure compatibility
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
    
    // Virtual property for name - used by findByNameContainingIgnoreCase
    public String getName() {
        return firstName + " " + lastName;
    }
}