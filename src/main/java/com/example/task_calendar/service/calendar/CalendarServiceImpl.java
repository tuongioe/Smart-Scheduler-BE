package com.example.task_calendar.service.calendar;

import com.example.task_calendar.dto.CalendarDTO;
import com.example.task_calendar.entity.Calendar;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.entity.User;
import com.example.task_calendar.repository.CalendarRepository;
import com.example.task_calendar.repository.UserRepository;
import com.example.task_calendar.util.DateUtil;
import com.example.task_calendar.util.UserUtil;
import org.dmfs.jems2.Predicate;
import org.dmfs.jems2.iterable.First;
import org.dmfs.jems2.iterator.While;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.RecurrenceSet;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;
import org.dmfs.rfc5545.recurrenceset.OfRule;
import org.dmfs.rfc5545.recurrenceset.Preceding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired

    private CalendarRepository calendarRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Calendar createCalendar(CalendarDTO calendarDTO) {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());

        Calendar calendar = new Calendar();
        calendar.setTitle(calendarDTO.getTitle());
        calendar.setColor(calendarDTO.getColor());
        calendar.setUser(user);


        Calendar createdCalendar = calendarRepository.save(calendar);

        user.add(calendar);

        return createdCalendar;
    }

    @Override
    public void deleteCalendar(long calendarId) {

    }


    @Override
    public List<Calendar> getUserCalendars(int year, int month, int day) {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());
        if (user != null) {
            List<Calendar> calendars = calendarRepository.findByUserId(user.getId());
            for (Calendar calendar : calendars) {
                List<Task> tasks = calendar.getTasks();
                List<Task> newTasks = new ArrayList<>();

                for (Task task : tasks) {
                    if (task.getIsRecurring()) {
                        try {
                            RecurrenceRule rule = new RecurrenceRule(task.getRecurrenceRule());
                            DateTime start = new DateTime(year, month - 1, day);
                            if (task.getEndDate() != null) {
                                DateTime end = new DateTime(task.getEndDate().getYear(), task.getEndDate().getMonthValue() - 1, task.getEndDate().getDayOfMonth());
                                for (DateTime occurrence : new Preceding(end, new OfRule(rule, start))) {
                                    Task tempTask = new Task();
                                    tempTask.setId(task.getId());
                                    tempTask.setTitle(task.getTitle());
                                    tempTask.setDescription(task.getDescription());
                                    LocalDateTime startTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getStartTime().getHour(), task.getStartTime().getMinute());
                                    tempTask.setStartTime(startTime);
                                    LocalDateTime endTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getEndTime().getHour(), task.getEndTime().getMinute());
                                    tempTask.setEndTime(endTime);
                                    tempTask.setIsRecurring(task.getIsRecurring());
                                    tempTask.setRecurrenceRule(task.getRecurrenceRule());
                                    tempTask.setEndDate(task.getEndDate());
                                    tempTask.setRepeatGap(task.getRepeatGap());
                                    tempTask.setNotificationNumber(task.getNotificationNumber());
                                    tempTask.setNotificationType(task.getNotificationType());
                                    tempTask.setCalendar(task.getCalendar());
                                    newTasks.add(tempTask);
                                }
                            } else {
                                for (DateTime occurrence : new First<>(366, new OfRule(rule, start))) {
                                    Task tempTask = new Task();
                                    tempTask.setId(task.getId());
                                    tempTask.setTitle(task.getTitle());
                                    tempTask.setDescription(task.getDescription());
                                    LocalDateTime startTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getStartTime().getHour(), task.getStartTime().getMinute());
                                    tempTask.setStartTime(startTime);
                                    LocalDateTime endTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getEndTime().getHour(), task.getEndTime().getMinute());
                                    tempTask.setEndTime(endTime);
                                    tempTask.setIsRecurring(task.getIsRecurring());
                                    tempTask.setRecurrenceRule(task.getRecurrenceRule());
                                    tempTask.setEndDate(task.getEndDate());
                                    tempTask.setRepeatGap(task.getRepeatGap());
                                    tempTask.setNotificationNumber(task.getNotificationNumber());
                                    tempTask.setNotificationType(task.getNotificationType());
                                    tempTask.setCalendar(task.getCalendar());
                                    newTasks.add(tempTask);
                                }
                            }

                        } catch (InvalidRecurrenceRuleException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                tasks.addAll(newTasks);
            }
            return calendars;
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<Calendar> getCalendars(int year, int month, int day) {
        List<Calendar> calendars = calendarRepository.findAll();
        for (Calendar calendar : calendars) {
            List<Task> tasks = calendar.getTasks();
            List<Task> newTasks = new ArrayList<>();

            for (Task task : tasks) {
                if (task.getIsRecurring()) {
                    try {
                        RecurrenceRule rule = new RecurrenceRule(task.getRecurrenceRule());
                        DateTime start = new DateTime(year, month - 1, day);
                        if (task.getEndDate() != null) {
                            DateTime end = new DateTime(task.getEndDate().getYear(), task.getEndDate().getMonthValue() - 1, task.getEndDate().getDayOfMonth());

                            for (DateTime occurrence : new Preceding(end, new OfRule(rule, start))) {
                                Task tempTask = new Task();
                                tempTask.setId(task.getId());
                                tempTask.setTitle(task.getTitle());
                                tempTask.setDescription(task.getDescription());
                                LocalDateTime startTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getStartTime().getHour(), task.getStartTime().getMinute());
                                tempTask.setStartTime(startTime);
                                LocalDateTime endTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getEndTime().getHour(), task.getEndTime().getMinute());
                                tempTask.setEndTime(endTime);
                                tempTask.setIsRecurring(task.getIsRecurring());
                                tempTask.setRecurrenceRule(task.getRecurrenceRule());
                                tempTask.setEndDate(task.getEndDate());
                                tempTask.setRepeatGap(task.getRepeatGap());
                                tempTask.setNotificationNumber(task.getNotificationNumber());
                                tempTask.setNotificationType(task.getNotificationType());
                                tempTask.setCalendar(task.getCalendar());
                                newTasks.add(tempTask);
                            }
                        } else {
                            for (DateTime occurrence : new First<>(366, new OfRule(rule, start))) {
                                Task tempTask = new Task();
                                tempTask.setId(task.getId());
                                tempTask.setTitle(task.getTitle());
                                tempTask.setDescription(task.getDescription());
                                LocalDateTime startTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getStartTime().getHour(), task.getStartTime().getMinute());
                                tempTask.setStartTime(startTime);
                                LocalDateTime endTime = LocalDateTime.of(occurrence.getYear(), occurrence.getMonth() + 1, occurrence.getDayOfMonth(), task.getEndTime().getHour(), task.getEndTime().getMinute());
                                tempTask.setEndTime(endTime);
                                tempTask.setIsRecurring(task.getIsRecurring());
                                tempTask.setRecurrenceRule(task.getRecurrenceRule());
                                tempTask.setEndDate(task.getEndDate());
                                tempTask.setRepeatGap(task.getRepeatGap());
                                tempTask.setNotificationNumber(task.getNotificationNumber());
                                tempTask.setNotificationType(task.getNotificationType());
                                tempTask.setCalendar(task.getCalendar());
                                newTasks.add(tempTask);
                            }
                        }

                    } catch (InvalidRecurrenceRuleException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            tasks.addAll(newTasks);
        }
        return calendars;
    }

    @Override
    public Calendar updateCalendar(Long id, CalendarDTO calendarDTO) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        if (calendarOptional.isPresent()) {
            Calendar calendar = calendarOptional.get();

            if (calendarDTO.getTitle() != null) {
                calendar.setTitle(calendarDTO.getTitle());
            }
            if (calendarDTO.getColor() != null) {
                calendar.setColor(calendarDTO.getColor());
            }

            calendarRepository.save(calendar);

            return calendar;

        } else {
            return null;
        }
    }

    @Override
    public Calendar deleteCalendar(Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        if (calendarOptional.isPresent()) {
            calendarRepository.deleteById(id);
            return calendarOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Calendar> getCalendarInfo() {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());
        List<Calendar> calendars = new ArrayList<>();
        for (Calendar calendar : calendarRepository.findByUserId(user.getId())) {
            Calendar tempCalendar = new Calendar();
            tempCalendar.setId(calendar.getId());
            tempCalendar.setTitle(calendar.getTitle());
            tempCalendar.setColor(calendar.getColor());
            calendars.add(tempCalendar);
        }
        return calendars;
    }

}
