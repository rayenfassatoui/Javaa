package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
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

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "student_subjects",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

    @Past(message = "Date of birth must be in the past")
    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }
    
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
    
    // Explicit getter for phone to ensure compatibility
    public String getPhone() {
        return phone;
    }
    
    // Explicit setter for phone to ensure compatibility
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    // Explicit getter for address to ensure compatibility
    public String getAddress() {
        return address;
    }
    
    // Explicit setter for address to ensure compatibility
    public void setAddress(String address) {
        this.address = address;
    }

    // Explicit getter for department to ensure compatibility
    public String getDepartment() {
        return department;
    }

    // Explicit setter for department to ensure compatibility
    public void setDepartment(String department) {
        this.department = department;
    }

    // Explicit getter for enrollmentYear to ensure compatibility
    public Integer getEnrollmentYear() {
        return enrollmentYear;
    }

    // Explicit setter for enrollmentYear to ensure compatibility
    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }
    
    // Explicit getter for registrationDate to ensure compatibility
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    // Explicit setter for registrationDate to ensure compatibility
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Explicit getter for subjects to ensure compatibility
    public Set<Subject> getSubjects() {
        return subjects;
    }

    // Explicit setter for subjects to ensure compatibility
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    // Explicit getter for dateOfBirth to ensure compatibility
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    // Explicit setter for dateOfBirth to ensure compatibility
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    // Convenience method to get dateOfBirth as java.sql.Date if needed
    public java.sql.Date getDateOfBirthAsSqlDate() {
        return dateOfBirth != null ? java.sql.Date.valueOf(dateOfBirth) : null;
    }

}