package com.springboot.tinyurlspringboot.controller;

import com.springboot.tinyurlspringboot.dtos.ShortenerDTO;
import com.springboot.tinyurlspringboot.model.shortener.Shortener;
import com.springboot.tinyurlspringboot.model.user.User;
import com.springboot.tinyurlspringboot.repositories.ShortenerRepository;
import com.springboot.tinyurlspringboot.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {
    private final UserRepository userRepository;
    private final ShortenerRepository shortenerRepository;

    public DashboardController(UserRepository userRepository, ShortenerRepository shortenerRepository) {
        this.userRepository = userRepository;
        this.shortenerRepository = shortenerRepository;
    }

    @PostMapping("/dashboard")
    public ResponseEntity<List<ShortenerDTO>> dashboard(@RequestBody Map<String,String> map) {
        String email=map.get("email");
        User user=userRepository.findByEmail(email);
        if (user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Shortener> list=shortenerRepository.findAllByUser(user);
        List<ShortenerDTO> shorts=list.stream().map(s->new ShortenerDTO(s.getOriginal(),s.getShortUrl(),s.getUrlName(),s.getClicks())).toList();
        if (list.isEmpty())return ResponseEntity.noContent().build();
        return ResponseEntity.ok(shorts);
    }
}
