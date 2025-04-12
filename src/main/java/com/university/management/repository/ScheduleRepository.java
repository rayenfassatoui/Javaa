package com.university.management.repository;

import com.university.management.model.ClassSubject;
import com.university.management.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    List<Schedule> findByClassSubject(ClassSubject classSubject);
    
    List<Schedule> findByDayOfWeek(Integer dayOfWeek);
    
    List<Schedule> findByRoom(String room);
    
    List<Schedule> findByStartTimeBetween(LocalTime startTime, LocalTime endTime);
    
    List<Schedule> findByDayOfWeekAndRoomAndStartTimeBetween(
            Integer dayOfWeek, String room, LocalTime startTime, LocalTime endTime);
} 