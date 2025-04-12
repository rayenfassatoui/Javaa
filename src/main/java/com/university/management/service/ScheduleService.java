package com.university.management.service;

import com.university.management.model.ClassSubject;
import com.university.management.model.Schedule;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    List<Schedule> getAllSchedules();
    
    Optional<Schedule> getScheduleById(Long id);
    
    List<Schedule> getSchedulesByClassSubject(ClassSubject classSubject);
    
    List<Schedule> getSchedulesByClassSubjectId(Long classSubjectId);
    
    List<Schedule> getSchedulesByDayOfWeek(Integer dayOfWeek);
    
    List<Schedule> getSchedulesByRoom(String room);
    
    List<Schedule> getSchedulesByTimeRange(LocalTime startTime, LocalTime endTime);
    
    List<Schedule> checkForConflicts(Integer dayOfWeek, String room, LocalTime startTime, LocalTime endTime);
    
    Schedule saveSchedule(Schedule schedule);
    
    void deleteSchedule(Long id);
    
    boolean hasScheduleConflict(Schedule schedule);
} 