package com.example.task_calendar.service.genTask;

import com.example.task_calendar.dto.GenerateTaskDTO.GenTaskRequest;
import com.example.task_calendar.dto.GenerateTaskDTO.GenTaskResponse;

public interface GenTaskService {
    GenTaskResponse generateTask(GenTaskRequest request);
}
