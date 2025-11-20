package com.springboot.tinyurlspringboot.controller;

import com.springboot.tinyurlspringboot.dtos.LoginDTO;
import com.springboot.tinyurlspringboot.dtos.SignupDTO;
import com.springboot.tinyurlspringboot.services.LoginService;
import com.springboot.tinyurlspringboot.services.UserAuthService;
import com.springboot.tinyurlspringboot.services.UserService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;
    UserService userService;
    UserAuthService userAuthService;

    AuthController(UserService userService, UserAuthService userAuthService, LoginService loginService) {
        this.userService = userService;
        this.userAuthService = userAuthService;
        this.loginService = loginService;
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO) {
    // 1. Keep this for good UX (fast fail)
        if (userService.userExists(signupDTO.getEmail())){
            return ResponseEntity.status(401).body("Already Registered");
        }

        try {
            // 2. Try to save
            userService.addUser(signupDTO.getEmail(), signupDTO.getPassword());
            return ResponseEntity.ok("Registered Successfully");
        
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(401).body("Already Registered");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        if (loginService.authenticate(loginDTO.getEmail(), loginDTO.getPassword())){
            return ResponseEntity.ok("Login Successful!");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
