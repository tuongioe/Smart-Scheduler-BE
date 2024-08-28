package com.example.task_calendar.service.calendar;

import com.example.task_calendar.dto.CalendarDTO;
import com.example.task_calendar.entity.Calendar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalendarService {
    Calendar createCalendar(CalendarDTO calendarDTO);

    void deleteCalendar(long calendarId);

    List<Calendar> getUserCalendars(int year, int month, int day);

    List<Calendar> getCalendars(int year, int month, int day);

    Calendar updateCalendar(Long id, CalendarDTO calendarDTO);

    Calendar deleteCalendar(Long id);

    List<Calendar> getCalendarInfo();
}
