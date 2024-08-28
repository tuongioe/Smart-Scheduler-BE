package com.example.task_calendar.dto.GenerateTaskDTO;

import java.time.LocalDateTime;

public record GenTaskResponse(
        String title,
        Integer calendarId,
        Repeat repeat,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isRecurring,
        Notification notification
) {
}
