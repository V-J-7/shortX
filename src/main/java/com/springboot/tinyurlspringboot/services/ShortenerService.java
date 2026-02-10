package com.springboot.tinyurlspringboot.services;

public class ShortenerService {

    // 1. Existing Base String
    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final long MULTIPLIER = 0x9E3779B97F4A7C15L;
    private static final long XOR_KEY = 5648913487L;

    public static String getShortURL(long code) {

        long scrambledId = code * MULTIPLIER;

        scrambledId = scrambledId ^ XOR_KEY;

        if (scrambledId < 0) {
            scrambledId = scrambledId & Long.MAX_VALUE;
        }
        StringBuilder ans = new StringBuilder();
        if (scrambledId == 0) {
            return "0";
        }
        while (scrambledId > 0) {
            ans.append(BASE.charAt((int)(scrambledId % 62)));
            scrambledId = scrambledId / 62;
        }
        return ans.reverse().toString();
    }
}