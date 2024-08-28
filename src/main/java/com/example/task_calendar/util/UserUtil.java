package com.example.task_calendar.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public String getCurrentUsername() {
        UserDetails userDetails = getCurrentUserDetails();

        if (userDetails != null) {
            return userDetails.getUsername();
        }
        return null;
    }
}
