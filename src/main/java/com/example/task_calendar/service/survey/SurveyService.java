package com.example.task_calendar.service.survey;

import com.example.task_calendar.dto.SurveyTaskDTO;
import com.example.task_calendar.entity.SurveyTask;

import java.util.List;

public interface SurveyService {
    List<SurveyTask> getAllSurveyTask();


    SurveyTask createOneSurveyTask(SurveyTaskDTO surveyTaskDTO);

    List<SurveyTask> createManySurveyTasks(List<SurveyTaskDTO> surveyTaskDTOList);

    List<SurveyTask> updateManySurveyTasks(List<SurveyTaskDTO> surveyTaskDTOList);
}
