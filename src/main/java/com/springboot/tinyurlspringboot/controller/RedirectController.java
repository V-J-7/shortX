package com.springboot.tinyurlspringboot.controller;

import com.springboot.tinyurlspringboot.model.shortener.Shortener;
import com.springboot.tinyurlspringboot.repositories.ShortenerRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

    private final ShortenerRepository shortenerRepository;

    public RedirectController(ShortenerRepository shortenerRepository) {
        this.shortenerRepository = shortenerRepository;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectUser(@PathVariable String shortCode) {

        // 1. Find the Shortener
        Shortener shortener = shortenerRepository.findByShortUrl(shortCode);

        if (shortener == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        shortenerRepository.incrementClicks(shortener.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(shortener.getOriginal()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}