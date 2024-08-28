package com.example.task_calendar.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CalendarDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Color cannot be blank")
    private String color;
}
