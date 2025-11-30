package com.springboot.tinyurlspringboot.dtos;

public class ShortenerDTO {
    String originalURL;
    String shortURL;
    String urlName;
    long clicks;

    public ShortenerDTO(String originalURL, String shortURL, String urlName, long clicks) {
        this.originalURL = originalURL;
        this.shortURL = shortURL;
        this.urlName = urlName;
        this.clicks = clicks;
    }
    public String getOriginalURL() {
        return originalURL;
    }
    public String getShortURL() {
        return shortURL;
    }
    public String geturlName() {
        return urlName;
    }
}
