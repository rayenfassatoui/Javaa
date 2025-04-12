package com.university.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_subjects", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"class_id", "subject_id", "teacher_id"})
})
public class ClassSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_subj_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    // Explicit getter for classEntity to ensure compatibility
    public Class getClassEntity() {
        return classEntity;
    }
    
    // Explicit setter for classEntity to ensure compatibility
    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }
    
    // Explicit getter for subject to ensure compatibility
    public Subject getSubject() {
        return subject;
    }
    
    // Explicit setter for subject to ensure compatibility
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    // Explicit getter for teacher to ensure compatibility
    public Teacher getTeacher() {
        return teacher;
    }
    
    // Explicit setter for teacher to ensure compatibility
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }
    
    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }
}