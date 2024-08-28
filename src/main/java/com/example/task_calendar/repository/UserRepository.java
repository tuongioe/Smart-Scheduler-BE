package com.example.task_calendar.repository;

import com.example.task_calendar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
