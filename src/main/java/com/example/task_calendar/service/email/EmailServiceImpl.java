package com.example.task_calendar.service.email;

import com.example.task_calendar.entity.Calendar;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.service.calendar.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    CalendarService calendarService;

    @Async
    public void sendEmail(String email, String subject, String body){
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom(emailSender);
        mailMsg.setTo(email);
        mailMsg.setSubject(subject);
        mailMsg.setText(body);
        try {
            javaMailSender.send(mailMsg);
            System.out.println("Email sent successfully.");
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }


}