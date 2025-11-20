package com.springboot.tinyurlspringboot.services;

import com.springboot.tinyurlspringboot.model.user.User;
import com.springboot.tinyurlspringboot.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final UserAuthService userAuthService;
    public LoginService(UserRepository userRepository, UserAuthService userAuthService) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }
    public boolean authenticate(String email, String password) {
        email = email.toLowerCase().trim();
        User user=userRepository.findByEmail(email);
        if (user==null)return false;
        return userAuthService.checkPassword(password,user.getPassword());
    }

}
