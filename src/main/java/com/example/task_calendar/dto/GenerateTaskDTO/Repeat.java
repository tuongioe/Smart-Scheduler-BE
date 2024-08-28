package com.example.task_calendar.dto.GenerateTaskDTO;

import java.util.List;

public record Repeat(
        RepeatType type,
        List<String> dayOfWeek,
        Long repeatGap,
        String endDate
) {
}
