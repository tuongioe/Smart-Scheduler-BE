package com.example.task_calendar.repository;

import com.example.task_calendar.entity.SurveyTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyTaskRepository extends JpaRepository<SurveyTask, Long> {
    List<SurveyTask> findByUserId(Long userId);
}
