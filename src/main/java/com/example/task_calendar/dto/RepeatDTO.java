package com.example.task_calendar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RepeatDTO {
    private String type;

    private List<String> dayOfWeek;

    private Long repeatGap;

    private String endDate;
}
