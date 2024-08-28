package com.example.task_calendar.service.task;

import com.example.task_calendar.dto.TaskDTO;
import com.example.task_calendar.entity.Calendar;
import com.example.task_calendar.entity.Task;
import com.example.task_calendar.repository.CalendarRepository;
import com.example.task_calendar.repository.TaskRepository;
import com.example.task_calendar.repository.UserRepository;
import com.example.task_calendar.util.DateUtil;
import com.example.task_calendar.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(taskDTO.getCalendarId());
        if (calendarOptional.isPresent()) {
            Calendar calendar = calendarOptional.get();

            Task task = new Task();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            LocalDateTime startTime = DateUtil.parseStringToLocalDateTime(taskDTO.getStartTime());
            task.setStartTime(startTime);
            LocalDateTime endTime = DateUtil.parseStringToLocalDateTime(taskDTO.getEndTime());
            task.setEndTime(endTime);
            task.setIsRecurring(taskDTO.getIsRecurring());

            if (taskDTO.getNotification() == null) {
                task.setNotificationType("minute");
                task.setNotificationNumber(30);
            } else {
                task.setNotificationNumber(taskDTO.getNotification().getNumber());
                task.setNotificationType(taskDTO.getNotification().getType());
            }
            if (taskDTO.getIsRecurring()) {
                taskDTO.createRecurrenceRule();
                task.setRecurrenceRule(taskDTO.getRecurrenceRule());
                if (taskDTO.getRepeat().getEndDate() != null) {
                    LocalDateTime endDate = DateUtil.parseStringToLocalDateTime(taskDTO.getRepeat().getEndDate());
                    task.setEndDate(endDate);
                }
                task.setRepeatGap(taskDTO.getRepeat().getRepeatGap());
            }
            task.setCalendar(calendar);

            Task createdTask = taskRepository.save(task);

            calendar.add(task);

            return createdTask;
        } else {
            return null;
        }
    }

    @Override
    public Task deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            taskRepository.deleteById(id);
            return taskOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public Task updateTask(Long id, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            if (taskDTO.getTitle() != null) {
                task.setTitle(taskDTO.getTitle());
            }
            if (taskDTO.getDescription() != null) {
                task.setDescription(taskDTO.getDescription());
            }
            if (taskDTO.getEndTime() != null) {
                LocalDateTime endTime = DateUtil.parseStringToLocalDateTime(taskDTO.getEndTime());
                task.setEndTime(endTime);
            }
            if (taskDTO.getStartTime() != null) {
                LocalDateTime startTime = DateUtil.parseStringToLocalDateTime(taskDTO.getStartTime());
                task.setStartTime(startTime);
            }
            if (taskDTO.getIsRecurring() != null) {
                task.setIsRecurring(taskDTO.getIsRecurring());
                if (taskDTO.getIsRecurring()) {
                    taskDTO.createRecurrenceRule();
                    task.setRecurrenceRule(taskDTO.getRecurrenceRule());
                    if (taskDTO.getRepeat().getEndDate() != null) {
                        LocalDateTime endDate = DateUtil.parseStringToLocalDateTime(taskDTO.getRepeat().getEndDate());
                        task.setEndDate(endDate);
                    }
                    task.setRepeatGap(taskDTO.getRepeat().getRepeatGap());
                }
            }
            if (taskDTO.getNotification() != null) {
                task.setNotificationNumber(taskDTO.getNotification().getNumber());
                task.setNotificationType(taskDTO.getNotification().getType());
            }


            taskRepository.save(task);

            return task;
        } else {
            return null;
        }
    }


    @Override
    public List<Task> createManyTasks(List<TaskDTO> taskDTOList) {
        List<Task> taskList = new ArrayList<>();
        // Handle each task dto
        for (TaskDTO taskDTO : taskDTOList) {
            Optional<Calendar> calendarOptional = calendarRepository.findById(taskDTO.getCalendarId());
            if (calendarOptional.isPresent()) {
                Calendar calendar = calendarOptional.get();

                Task task = new Task();
                task.setTitle(taskDTO.getTitle());
                task.setDescription(taskDTO.getDescription());
                LocalDateTime startTime = DateUtil.parseStringToLocalDateTime(taskDTO.getStartTime());
                task.setStartTime(startTime);
                LocalDateTime endTime = DateUtil.parseStringToLocalDateTime(taskDTO.getEndTime());
                task.setEndTime(endTime);
                task.setIsRecurring(taskDTO.getIsRecurring());

                if (taskDTO.getNotification() == null) {
                    task.setNotificationType("minute");
                    task.setNotificationNumber(30);
                } else {
                    task.setNotificationNumber(taskDTO.getNotification().getNumber());
                    task.setNotificationType(taskDTO.getNotification().getType());
                }
                if (taskDTO.getIsRecurring()) {
                    taskDTO.createRecurrenceRule();
                    task.setRecurrenceRule(taskDTO.getRecurrenceRule());
                    if (taskDTO.getRepeat().getEndDate() != null) {
                        LocalDateTime endDate = DateUtil.parseStringToLocalDateTime(taskDTO.getRepeat().getEndDate());
                        task.setEndDate(endDate);
                    }
                    task.setRepeatGap(taskDTO.getRepeat().getRepeatGap());
                }
                task.setCalendar(calendar);

                calendar.add(task);

                taskList.add(task);
            } else {
                return null;
            }
        }

        return taskRepository.saveAll(taskList);
    }

}
