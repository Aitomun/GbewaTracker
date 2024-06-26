package com.eromosele.gbewatracker.controller;

import com.eromosele.gbewatracker.dto.LoginRequestDto;
import com.eromosele.gbewatracker.dto.LoginResponse;
import com.eromosele.gbewatracker.dto.UserRegistrationRequest;
import com.eromosele.gbewatracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest registrationRequest){
        try{
            userService.registerUser(registrationRequest);
            return ResponseEntity.ok("User registered successfully. Please check your email to confirm your account");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.loginUser(loginRequestDto));
    }
}
