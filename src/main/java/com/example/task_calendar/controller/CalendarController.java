package com.example.task_calendar.controller;

import com.example.task_calendar.dto.CalendarDTO;
import com.example.task_calendar.dto.SignUpDTO;
import com.example.task_calendar.dto.TaskDTO;
import com.example.task_calendar.dto.UserDTO;
import com.example.task_calendar.entity.Calendar;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.entity.User;
import com.example.task_calendar.exception.ApiRequestException;
import com.example.task_calendar.response.ResponseHandler;
import com.example.task_calendar.service.calendar.CalendarService;
import com.example.task_calendar.util.UserUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/calendar")
    public ResponseEntity<?> addCalendar(@Valid @RequestBody CalendarDTO calendarDTO) {

        Calendar calendar = calendarService.createCalendar(calendarDTO);

        return ResponseHandler.responseBuilder("Created calendar successfully", HttpStatus.OK, calendar);
    }

    @DeleteMapping("/calendar/{id}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long id) {
        Calendar calendar = calendarService.deleteCalendar(id);
        if (calendar == null) {
            throw new ApiRequestException("calendarID doesn't exist.");
        }
        return ResponseHandler.responseBuilder("Deleted calendar with id= " + calendar.getId() + " successfully", HttpStatus.OK, null);
    }

    @PatchMapping("/calendar/{id}")
    public ResponseEntity<?> updateCalendar(@PathVariable Long id, @RequestBody CalendarDTO calendarDTO) {
        Calendar calendar = calendarService.updateCalendar(id, calendarDTO);
        if (calendar == null) {
            throw new ApiRequestException("calendarID doesn't exist.");
        }
        return ResponseHandler.responseBuilder("Updated calendar with id= " + calendar.getId() + " successfully", HttpStatus.OK, null);
    }

    @GetMapping("/calendar/{type}/{year}/{month}/{day}")
    public ResponseEntity<?> getCalendarWithTasks(@PathVariable String type, @PathVariable int year, @PathVariable int month, @PathVariable int day) {

        List<Calendar> calendars = new ArrayList<>();
        LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);
        switch (type) {
            case "week":
                LocalDateTime startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDateTime endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).withHour(23).withMinute(59);
                calendars = calendarService.getUserCalendars(startOfWeek.getYear(), startOfWeek.getMonthValue(), startOfWeek.getDayOfMonth());
                for (Calendar calendar : calendars) {
                    List<Task> filteredTasks = calendar.getTasks().stream()
                            .filter(task -> {
                                LocalDateTime dateTime = task.getStartTime();
                                return dateTime.isEqual(startOfWeek) || dateTime.isEqual(endOfWeek) ||
                                        (dateTime.isAfter(startOfWeek) && dateTime.isBefore(endOfWeek));
                            }).sorted(Comparator.comparing(Task::getStartTime))
                            .collect(Collectors.toList());
                    calendar.setTasks(filteredTasks);
                }
                break;
            case "month":
                LocalDateTime startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0);
                LocalDateTime endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59);
                calendars = calendarService.getUserCalendars(startOfMonth.getYear(), startOfMonth.getMonthValue(), startOfMonth.getDayOfMonth());
                for (Calendar calendar : calendars) {
                    List<Task> filteredTasks = calendar.getTasks().stream()
                            .filter(task -> {
                                LocalDateTime dateTime = task.getStartTime();
                                return dateTime.isEqual(startOfMonth) || dateTime.isEqual(endOfMonth) ||
                                        (dateTime.isAfter(startOfMonth) && dateTime.isBefore(endOfMonth));
                            }).sorted(Comparator.comparing(Task::getStartTime))
                            .collect(Collectors.toList());
                    calendar.setTasks(filteredTasks);
                }
                break;
            case "day":
                LocalDateTime startOfDay = date.withHour(0).withMinute(0);
                LocalDateTime endOfDay = date.withHour(23).withMinute(59);
                calendars = calendarService.getUserCalendars(startOfDay.getYear(), startOfDay.getMonthValue(), startOfDay.getDayOfMonth());
                for (Calendar calendar : calendars) {
                    List<Task> filteredTasks = calendar.getTasks().stream()
                            .filter(task -> {
                                LocalDateTime dateTime = task.getStartTime();
                                return dateTime.isEqual(startOfDay) || dateTime.isEqual(endOfDay) ||
                                        (dateTime.isAfter(startOfDay) && dateTime.isBefore(endOfDay));
                            }).sorted(Comparator.comparing(Task::getStartTime))
                            .collect(Collectors.toList());

                    calendar.setTasks(filteredTasks);
                }
                break;

            case "year":
                LocalDateTime startOfYear = date.with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0);
                LocalDateTime endOfYear = date.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59);
                calendars = calendarService.getUserCalendars(startOfYear.getYear(), startOfYear.getMonthValue(), startOfYear.getDayOfMonth());
                for (Calendar calendar : calendars) {
                    List<Task> filteredTasks = calendar.getTasks().stream()
                            .filter(task -> {
                                LocalDateTime dateTime = task.getStartTime();
                                return dateTime.isEqual(startOfYear) || dateTime.isEqual(endOfYear) ||
                                        (dateTime.isAfter(startOfYear) && dateTime.isBefore(endOfYear));
                            }).sorted(Comparator.comparing(Task::getStartTime))
                            .collect(Collectors.toList());
                    calendar.setTasks(filteredTasks);
                }
                break;
            default:
                // code block
        }

        return ResponseHandler.responseBuilder("Get All Calendar With Tasks successfully", HttpStatus.OK, calendars);
    }

    @GetMapping("/calendars")
    public ResponseEntity<?> getCalendarInfo() {
        List<Calendar> calendars = calendarService.getCalendarInfo();
        return ResponseHandler.responseBuilder("Get all calendar info successfully", HttpStatus.OK, calendars);
    }
}
