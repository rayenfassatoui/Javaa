package com.university.management.controller;

import com.university.management.model.ClassSubject;
import com.university.management.model.Schedule;
import com.university.management.service.ClassSubjectService;
import com.university.management.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ClassSubjectService classSubjectService;

    @Autowired
    public ScheduleController(
            ScheduleService scheduleService,
            ClassSubjectService classSubjectService) {
        this.scheduleService = scheduleService;
        this.classSubjectService = classSubjectService;
    }

    @GetMapping
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "schedules/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("classSubjects", classSubjectService.getAllClassSubjects());
        model.addAttribute("daysOfWeek", getDaysOfWeek());
        return "schedules/form";
    }

    @PostMapping
    public String createSchedule(@Valid @ModelAttribute Schedule schedule, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes,
                               Model model) {
        // Check for schedule conflicts
        if (scheduleService.hasScheduleConflict(schedule)) {
            result.rejectValue("startTime", "error.schedule", 
                    "Schedule conflict detected. Another class is scheduled in the same room at the same time.");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("classSubjects", classSubjectService.getAllClassSubjects());
            model.addAttribute("daysOfWeek", getDaysOfWeek());
            return "schedules/form";
        }
        
        try {
            scheduleService.saveSchedule(schedule);
            redirectAttributes.addFlashAttribute("successMessage", "Schedule has been created successfully!");
            return "redirect:/schedules";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/schedules/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        
        if (schedule.isPresent()) {
            model.addAttribute("schedule", schedule.get());
            model.addAttribute("classSubjects", classSubjectService.getAllClassSubjects());
            model.addAttribute("daysOfWeek", getDaysOfWeek());
            return "schedules/form";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Schedule not found!");
            return "redirect:/schedules";
        }
    }

    @GetMapping("/{id}")
    public String viewSchedule(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        
        if (schedule.isPresent()) {
            model.addAttribute("schedule", schedule.get());
            model.addAttribute("dayName", getDayName(schedule.get().getDayOfWeek()));
            return "schedules/view";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Schedule not found!");
            return "redirect:/schedules";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteSchedule(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            scheduleService.deleteSchedule(id);
            redirectAttributes.addFlashAttribute("successMessage", "Schedule has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting schedule: " + e.getMessage());
        }
        return "redirect:/schedules";
    }

    @GetMapping("/class-subject/{classSubjectId}")
    public String getSchedulesByClassSubject(@PathVariable Long classSubjectId, Model model, RedirectAttributes redirectAttributes) {
        Optional<ClassSubject> classSubject = classSubjectService.getClassSubjectById(classSubjectId);
        
        if (classSubject.isPresent()) {
            List<Schedule> schedules = scheduleService.getSchedulesByClassSubjectId(classSubjectId);
            model.addAttribute("schedules", schedules);
            model.addAttribute("classSubject", classSubject.get());
            return "schedules/class-subject-schedules";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Class-Subject assignment not found!");
            return "redirect:/schedules";
        }
    }

    @GetMapping("/day/{dayOfWeek}")
    public String getSchedulesByDay(@PathVariable Integer dayOfWeek, Model model, RedirectAttributes redirectAttributes) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid day of week!");
            return "redirect:/schedules";
        }
        
        List<Schedule> schedules = scheduleService.getSchedulesByDayOfWeek(dayOfWeek);
        model.addAttribute("schedules", schedules);
        model.addAttribute("dayName", getDayName(dayOfWeek));
        return "schedules/list";
    }

    @GetMapping("/room/{room}")
    public String getSchedulesByRoom(@PathVariable String room, Model model) {
        List<Schedule> schedules = scheduleService.getSchedulesByRoom(room);
        model.addAttribute("schedules", schedules);
        model.addAttribute("room", room);
        return "schedules/list";
    }

    @GetMapping("/by-time-range")
    public String getSchedulesByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            Model model) {
        
        List<Schedule> schedules = scheduleService.getSchedulesByTimeRange(startTime, endTime);
        model.addAttribute("schedules", schedules);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "schedules/list";
    }

    @GetMapping("/check-conflicts")
    public String showCheckConflictsForm(Model model) {
        model.addAttribute("daysOfWeek", getDaysOfWeek());
        return "schedules/check-conflicts";
    }

    @PostMapping("/check-conflicts")
    public String checkConflicts(
            @RequestParam Integer dayOfWeek,
            @RequestParam String room,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            Model model) {
        
        List<Schedule> conflictingSchedules = scheduleService.checkForConflicts(dayOfWeek, room, startTime, endTime);
        model.addAttribute("conflictingSchedules", conflictingSchedules);
        model.addAttribute("dayOfWeek", dayOfWeek);
        model.addAttribute("dayName", getDayName(dayOfWeek));
        model.addAttribute("room", room);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "schedules/conflict-results";
    }

    private List<DayOfWeek> getDaysOfWeek() {
        return Arrays.asList(
            new DayOfWeek(1, "Monday"),
            new DayOfWeek(2, "Tuesday"),
            new DayOfWeek(3, "Wednesday"),
            new DayOfWeek(4, "Thursday"),
            new DayOfWeek(5, "Friday"),
            new DayOfWeek(6, "Saturday"),
            new DayOfWeek(7, "Sunday")
        );
    }

    private String getDayName(Integer dayOfWeek) {
        return switch (dayOfWeek) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6 -> "Saturday";
            case 7 -> "Sunday";
            default -> "Unknown";
        };
    }

    // Helper class for day of week
    public static class DayOfWeek {
        private final Integer value;
        private final String name;

        public DayOfWeek(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
} 