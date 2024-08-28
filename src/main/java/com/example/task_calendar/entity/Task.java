package com.example.task_calendar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_recurring")
    private Boolean isRecurring;

    @Column(name = "recurrence_rule")
    private String recurrenceRule;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "repeat_gap")
    private Long repeatGap;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "notification_number")
    private int notificationNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "calendar_id")
    @JsonIgnoreProperties("tasks")
    private Calendar calendar;
}
