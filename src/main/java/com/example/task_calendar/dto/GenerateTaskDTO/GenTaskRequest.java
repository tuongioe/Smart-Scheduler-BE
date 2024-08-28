package com.example.task_calendar.dto.GenerateTaskDTO;

public record GenTaskRequest(
        String title,
        Integer estimatedTime,
        Integer calendarId,
        Repeat repeat,
        String description,
        String[] existTimes,
        boolean isRecurring,
        Notification notification
) {
}
