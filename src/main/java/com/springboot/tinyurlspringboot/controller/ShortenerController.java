package com.springboot.tinyurlspringboot.controller;

import com.springboot.tinyurlspringboot.services.ShortenerService;
import com.springboot.tinyurlspringboot.utils.URLValidator;
import com.springboot.tinyurlspringboot.model.shortener.Shortener;
import com.springboot.tinyurlspringboot.model.user.User;
import com.springboot.tinyurlspringboot.repositories.ShortenerRepository;
import com.springboot.tinyurlspringboot.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ShortenerController {
    private final ShortenerRepository shortenerRepository;
    private final UserRepository userRepository;

    public ShortenerController(ShortenerRepository shortenerRepository, UserRepository userRepository) {
        this.shortenerRepository = shortenerRepository;
        this.userRepository = userRepository;
    }
    @PostMapping("/shorten")
    public ResponseEntity<String> shortener(@RequestBody Map<String,String> map) {
        String email = map.get("email");
        String original = map.get("original");
        String shortURL = "";
        String urlName=map.get("urlName");
        if (!URLValidator.isValidURL(original)){
            return ResponseEntity.badRequest().body("Invalid URL");
        }
        User user=userRepository.findByEmail(email);
        if (user==null){
            return ResponseEntity.status(404).body("User not found");
        }
        original = URLValidator.normalize(original);
        Shortener existing = shortenerRepository.findByOriginalAndUser(original,user);
        if (existing != null) {
            return ResponseEntity.ok(existing.getShortUrl());
        }

        Long nextId = shortenerRepository.getNextSeriesId();
        String generatedShortUrl = ShortenerService.getShortURL(nextId);
        Shortener newShortener = new Shortener(original, generatedShortUrl, urlName, user);
        newShortener.setId(nextId);
        shortenerRepository.save(newShortener);

        return ResponseEntity.ok(generatedShortUrl);

    }

    @PostMapping("/delete")
    public void delete(@RequestBody Map<String,String> map) {
        String email = map.get("email");
        String shortURL = map.get("shortURL");
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Shortener shortener = shortenerRepository.findByShortUrlAndUser(shortURL, user);
            if (shortener != null) {
                shortenerRepository.delete(shortener);
            }
        }
    }}
