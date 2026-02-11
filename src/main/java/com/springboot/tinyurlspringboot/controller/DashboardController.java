package com.springboot.tinyurlspringboot.controller;

import com.springboot.tinyurlspringboot.dtos.ShortenerDTO;
import com.springboot.tinyurlspringboot.model.shortener.Shortener;
import com.springboot.tinyurlspringboot.model.user.User;
import com.springboot.tinyurlspringboot.repositories.ShortenerRepository;
import com.springboot.tinyurlspringboot.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<ShortenerDTO>> dashboard(@RequestBody Map<String, Object> map) {
        String email = (String) map.get("email");

        int page = map.containsKey("page") ? (int) map.get("page") : 0;
        int size = map.containsKey("size") ? (int) map.get("size") : 10;

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Shortener> pageResult = shortenerRepository.findByUser(user, pageable);

        List<ShortenerDTO> shorts = pageResult.stream()
                .map(s -> new ShortenerDTO(s.getOriginal(), s.getShortUrl(), s.getUrlName(), s.getClicks()))
                .toList();

        return ResponseEntity.ok(shorts);
    }
}