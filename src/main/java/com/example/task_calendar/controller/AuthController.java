package com.example.task_calendar.controller;
import com.example.task_calendar.dto.CalendarDTO;
import com.example.task_calendar.dto.LoginDTO;
import com.example.task_calendar.dto.SignUpDTO;
import com.example.task_calendar.dto.UserDTO;
import com.example.task_calendar.entity.User;
import com.example.task_calendar.exception.ApiRequestException;
import com.example.task_calendar.response.ResponseHandler;
import com.example.task_calendar.service.auth.AuthService;
import com.example.task_calendar.service.jwt.UserDetailsServiceImpl;
import com.example.task_calendar.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignUpDTO signupDTO) {

        UserDTO createdUser = authService.createUser(signupDTO);
        if (createdUser == null){
            throw new ApiRequestException("Email already exists");

        }

        return ResponseHandler.responseBuilder("Sign up successfully", HttpStatus.OK, createdUser);
    }



    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ApiRequestException("Incorrect email or password!");

        } catch (DisabledException disabledException) {
            throw new ApiRequestException("Incorrect email or password!");

        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", jwt);


        return ResponseHandler.responseBuilder("Login successfully", HttpStatus.OK, responseMap);
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getCurrentUser() {

        User user = authService.getCurrentUser();
        return ResponseHandler.responseBuilder("Get current user successfully", HttpStatus.OK, user);
    }
}
