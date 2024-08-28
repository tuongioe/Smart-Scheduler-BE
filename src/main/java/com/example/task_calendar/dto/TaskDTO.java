package com.example.task_calendar.dto;

import com.example.task_calendar.util.DateUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TaskDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;
    @NotBlank(message = "startTime cannot be blank")
    private String startTime;
    @NotBlank(message = "endTime cannot be blank")
    private String endTime;
    @NotNull(message = "calendarID cannot be null")
    private Long calendarId;
    @NotNull(message = "isRecurring cannot be null")
    private Boolean isRecurring;
    private String recurrenceRule;
    private RepeatDTO repeat;
    private NotificationDTO notification;

    public void createRecurrenceRule() {
        String rule="FREQ=";
        switch (this.repeat.getType()) {
            case "week":
                StringBuilder ruleBuilder = new StringBuilder("WEEKLY;BYDAY=");
                List<String> daysOfWeek = this.getRepeat().getDayOfWeek();
                for (int i = 0; i < daysOfWeek.size(); i++) {
                    String day = daysOfWeek.get(i);
                    ruleBuilder.append(day.substring(0, 2).toUpperCase());
                    if (i < daysOfWeek.size() - 1) {
                        ruleBuilder.append(",");
                    }
                }
                rule = rule + ruleBuilder.toString();
                break;
            case "month":
                rule = rule + "MONTHLY;BYMONTHDAY=" + DateUtil.parseStringToLocalDateTime(this.getStartTime()).getDayOfMonth();
                break;
            case "year":
                rule = rule + "YEARLY;BYMONTHDAY=" + DateUtil.parseStringToLocalDateTime(this.getStartTime()).getDayOfMonth() + ";BYMONTH=" + DateUtil.parseStringToLocalDateTime(this.getStartTime()).getMonthValue();
                break;
            case "day":
                rule = rule + "DAILY";
                break;


        }
        if(this.repeat.getRepeatGap() != null) {
            rule= rule + ";INTERVAL=" + (this.getRepeat().getRepeatGap() + 1);
        }
        this.recurrenceRule = rule;
    }
}
