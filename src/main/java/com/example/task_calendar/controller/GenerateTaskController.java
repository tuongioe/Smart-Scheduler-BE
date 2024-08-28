package com.example.task_calendar.controller;

import com.example.task_calendar.dto.GenerateTaskDTO.GenTaskRequest;
import com.example.task_calendar.dto.GenerateTaskDTO.GenTaskResponse;
import com.example.task_calendar.dto.TaskDTO;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.exception.ApiRequestException;
import com.example.task_calendar.response.ResponseHandler;
import com.example.task_calendar.service.genTask.GenTaskService;

import com.example.task_calendar.service.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenerateTaskController {

    private final GenTaskService genTaskService;
    private final TaskService taskService;

    @PostMapping("/generate/task")
    public GenTaskResponse generateTask(@RequestBody GenTaskRequest request) {
        return genTaskService.generateTask(request);
    }

    @PostMapping("/create-many-tasks")
    public List<Task> saveManyTasks(@RequestBody @Valid List<TaskDTO> taskDTOList) {
        List<Task> tasks = taskService.createManyTasks(taskDTOList);

        for (Task task : tasks) {
            if (task == null) throw new ApiRequestException("calendarID doesn't exist");
        }

        return tasks;


//        return ResponseHandler.responseBuilder("Created task successfully", HttpStatus.OK, task);
    }
}
