package com.springboot.tinyurlspringboot.services;

public class ShortenerService {

    // 1. Existing Base String
    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final long SECRET_KEY = 4829103845L;

    public static String getShortURL(long code) {

        code = code ^ SECRET_KEY;

        StringBuilder ans = new StringBuilder();
        if (code == 0) {
            return "0";
        }
        while (code > 0) {
            ans.append(BASE.charAt((int)(code % 62)));
            code = code / 62;
        }
        return ans.reverse().toString();
    }
}