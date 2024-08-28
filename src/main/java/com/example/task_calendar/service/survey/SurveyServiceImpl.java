package com.example.task_calendar.service.survey;

import com.example.task_calendar.dto.SurveyTaskDTO;
import com.example.task_calendar.entity.SurveyTask;
import com.example.task_calendar.entity.User;
import com.example.task_calendar.repository.SurveyTaskRepository;
import com.example.task_calendar.repository.UserRepository;
import com.example.task_calendar.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final SurveyTaskRepository surveyTaskRepository;

    @Override
    public List<SurveyTask> getAllSurveyTask() {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());
        return surveyTaskRepository.findByUserId(user.getId());
    }

    @Override
    public SurveyTask createOneSurveyTask(SurveyTaskDTO surveyTaskDTO) {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());

        SurveyTask surveyTask = new SurveyTask();
        surveyTask.setTitle(surveyTaskDTO.title());
        surveyTask.setStartTime(surveyTaskDTO.startTime());
        surveyTask.setUser(user);

        user.add(surveyTask);

        return surveyTaskRepository.save(surveyTask);
    }

    @Override
    public List<SurveyTask> createManySurveyTasks(List<SurveyTaskDTO> surveyTaskDTOList) {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());
        List<SurveyTask> surveyTaskList = new ArrayList<>();

        for (SurveyTaskDTO surveyTaskDTO : surveyTaskDTOList) {
            SurveyTask surveyTask = new SurveyTask();
            surveyTask.setTitle(surveyTaskDTO.title());
            surveyTask.setStartTime(surveyTaskDTO.startTime());
            surveyTask.setUser(user);

            user.add(surveyTask);
            surveyTaskList.add(surveyTask);
        }

        return surveyTaskRepository.saveAll(surveyTaskList);
    }

    @Override
    public List<SurveyTask> updateManySurveyTasks(List<SurveyTaskDTO> surveyTaskDTOList) {
        User user = userRepository.findFirstByEmail(userUtil.getCurrentUsername());
        List<SurveyTask> surveyTaskList = new ArrayList<>();

        for (SurveyTaskDTO surveyTaskDTO : surveyTaskDTOList) {
            Optional<SurveyTask> surveyTask = surveyTaskRepository.findById(surveyTaskDTO.id());
            if (surveyTask.isPresent()) {
                surveyTask.get().setTitle(surveyTaskDTO.title());
                surveyTask.get().setStartTime(surveyTaskDTO.startTime());

                user.add(surveyTask.get());
                surveyTaskList.add(surveyTask.get());
            }
        }

        return surveyTaskRepository.saveAll(surveyTaskList);
    }

}
