package com.example.task_calendar.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static LocalDateTime parseStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}