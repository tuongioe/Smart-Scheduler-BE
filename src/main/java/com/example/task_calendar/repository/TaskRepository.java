package com.example.task_calendar.repository;


import com.example.task_calendar.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
