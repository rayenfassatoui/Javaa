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

    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }
    
    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }
    
    // Explicit getter for name to ensure compatibility
    public String getName() {
        return name;
    }
    
    // Explicit setter for name to ensure compatibility
    public void setName(String name) {
        this.name = name;
    }
    
    // Explicit getter for capacity to ensure compatibility
    public Integer getCapacity() {
        return capacity;
    }
    
    // Explicit setter for capacity to ensure compatibility
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    // Explicit getter for room to ensure compatibility
    public String getRoom() {
        return room;
    }
    
    // Explicit setter for room to ensure compatibility
    public void setRoom(String room) {
        this.room = room;
    }
    
    // Explicit getter for department to ensure compatibility
    public String getDepartment() {
        return department;
    }
    
    // Explicit setter for department to ensure compatibility
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Explicit getter for year to ensure compatibility
    public Integer getYear() {
        return year;
    }
    
    // Explicit setter for year to ensure compatibility
    public void setYear(Integer year) {
        this.year = year;
    }
}