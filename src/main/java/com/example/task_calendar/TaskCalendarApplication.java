package com.example.task_calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskCalendarApplication.class, args);
	}

}
