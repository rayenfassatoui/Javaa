package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    // Explicit getter for id to ensure compatibility
    public Long getId() {
        return id;
    }

    // Explicit setter for id to ensure compatibility
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "class_subj_id", nullable = false)
    private ClassSubject classSubject;

    // Explicit getter for classSubject to ensure compatibility
    public ClassSubject getClassSubject() {
        return classSubject;
    }

    // Explicit setter for classSubject to ensure compatibility
    public void setClassSubject(ClassSubject classSubject) {
        this.classSubject = classSubject;
    }

    @Min(value = 1, message = "Day of week must be between 1 and 7")
    @Max(value = 7, message = "Day of week must be between 1 and 7")
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    // Explicit getter for dayOfWeek to ensure compatibility
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    // Explicit setter for dayOfWeek to ensure compatibility
    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // Explicit getter for startTime to ensure compatibility
    public LocalTime getStartTime() {
        return startTime;
    }

    // Explicit setter for startTime to ensure compatibility
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Explicit getter for endTime to ensure compatibility
    public LocalTime getEndTime() {
        return endTime;
    }

    // Explicit setter for endTime to ensure compatibility
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Column(name = "room", length = 20)
    private String room;

    // Explicit getter for room to ensure compatibility
    public String getRoom() {
        return room;
    }

    // Explicit setter for room to ensure compatibility
    public void setRoom(String room) {
        this.room = room;
    }
}