package com.example.task_calendar.service.email;

import com.example.task_calendar.entity.Calendar;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.repository.CalendarRepository;
import com.example.task_calendar.service.calendar.CalendarService;
import com.example.task_calendar.service.calendar.CalendarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleEmailServiceImpl implements ScheduleEmailService{

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CalendarRepository calendarRepository;
    @Override
    @Scheduled(fixedRate = 60000)
    @Transactional(readOnly = true)
    public void checkToSendEmail() {

        LocalDateTime now = LocalDateTime.now();

        List<Calendar> calendars= calendarRepository.findAll();


        for (Calendar calendar : calendars) {

            String recipientEmail =  calendar.getUser().getEmail();

            for (Task event : calendar.getTasks()) {

                String msg = "This is a reminder for your task in "+ event.getNotificationNumber();
                long minutesUntilEvent = ChronoUnit.MINUTES.between(now, event.getStartTime());
                long minutes = 0;

                switch(event.getNotificationType()) {
                    case "day":
                        minutes = event.getNotificationNumber() * 24 * 60;
                        msg = msg + " days";
                        break;

                    case "hour":
                        minutes = event.getNotificationNumber() * 60;
                        msg = msg + " hours";
                        break;

                    case "minute":
                        minutes = event.getNotificationNumber();
                        msg = msg + " minutes";
                        break;

                }

                if (minutesUntilEvent == minutes) {
                    emailService.sendEmail(recipientEmail, "Reminder", msg);
                }
            }
        }
    }


}

