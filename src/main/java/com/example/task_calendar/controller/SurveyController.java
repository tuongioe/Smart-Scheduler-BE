package com.example.task_calendar.controller;

import com.example.task_calendar.dto.SurveyTaskDTO;
import com.example.task_calendar.entity.SurveyTask;
import com.example.task_calendar.response.ResponseHandler;
import com.example.task_calendar.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey")
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping("/create-single")
    ResponseEntity<Object> createSurveyTask(@RequestBody SurveyTaskDTO surveyTaskDTO) {
        SurveyTask surveyTask = surveyService.createOneSurveyTask(surveyTaskDTO);
        return ResponseHandler.responseBuilder("Created survey successfully", HttpStatus.CREATED, surveyTask);
    }

    @PostMapping()
    ResponseEntity<Object> createSurveyTask(@RequestBody List<SurveyTaskDTO> surveyTaskDTOList) {
        List<SurveyTask> surveyTasks = surveyService.createManySurveyTasks(surveyTaskDTOList);
        return ResponseHandler.responseBuilder("Created survey successfully", HttpStatus.CREATED, surveyTasks);
    }

    @GetMapping()
    ResponseEntity<Object> getAllSurveyTasks() {
        List<SurveyTask> surveyTasks = surveyService.getAllSurveyTask();
        return ResponseHandler.responseBuilder("Get all surveys successfully", HttpStatus.OK, surveyTasks);
    }

    @PatchMapping()
    ResponseEntity<Object> updateSurveyTask(@RequestBody List<SurveyTaskDTO> surveyTaskDTOList) {
        List<SurveyTask> surveyTasks = surveyService.updateManySurveyTasks(surveyTaskDTOList);
        return ResponseHandler.responseBuilder("Updated survey successfully", HttpStatus.OK, surveyTasks);
    }
}
