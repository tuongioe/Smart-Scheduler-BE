package com.example.task_calendar.service.auth;


import com.example.task_calendar.dto.SignUpDTO;
import com.example.task_calendar.dto.UserDTO;
import com.example.task_calendar.entity.User;

public interface AuthService {
    UserDTO createUser(SignUpDTO signupDTO);
    User getCurrentUser();
}
