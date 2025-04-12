package com.university.management.service.impl;

import com.university.management.model.ClassSubject;
import com.university.management.model.Schedule;
import com.university.management.repository.ClassSubjectRepository;
import com.university.management.repository.ScheduleRepository;
import com.university.management.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClassSubjectRepository classSubjectRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ClassSubjectRepository classSubjectRepository) {
        this.scheduleRepository = scheduleRepository;
        this.classSubjectRepository = classSubjectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByClassSubject(ClassSubject classSubject) {
        return scheduleRepository.findByClassSubject(classSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByClassSubjectId(Long classSubjectId) {
        Optional<ClassSubject> classSubject = classSubjectRepository.findById(classSubjectId);
        return classSubject.map(scheduleRepository::findByClassSubject).orElse(List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByDayOfWeek(Integer dayOfWeek) {
        return scheduleRepository.findByDayOfWeek(dayOfWeek);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByRoom(String room) {
        return scheduleRepository.findByRoom(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByTimeRange(LocalTime startTime, LocalTime endTime) {
        return scheduleRepository.findByStartTimeBetween(startTime, endTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> checkForConflicts(Integer dayOfWeek, String room, LocalTime startTime, LocalTime endTime) {
        return scheduleRepository.findByDayOfWeekAndRoomAndStartTimeBetween(dayOfWeek, room, startTime, endTime);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        // Check for conflicts before saving
        if (hasScheduleConflict(schedule)) {
            throw new IllegalStateException("Schedule conflict detected. Another class is scheduled in the same room at the same time.");
        }
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasScheduleConflict(Schedule schedule) {
        // Don't check conflicts with self (for updates)
        List<Schedule> conflictingSchedules = scheduleRepository.findByDayOfWeekAndRoomAndStartTimeBetween(
                schedule.getDayOfWeek(), 
                schedule.getRoom(), 
                schedule.getStartTime().minusMinutes(60), // Check 1 hour before
                schedule.getEndTime());
        
        // Filter out self from conflicts if it's an update
        if (schedule.getId() != null) {
            conflictingSchedules = conflictingSchedules.stream()
                    .filter(s -> !s.getId().equals(schedule.getId()))
                    .toList();
        }
        
        return !conflictingSchedules.isEmpty();
    }
} 