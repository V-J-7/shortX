package com.springboot.tinyurlspringboot.services;

public class ShortenerService {
    private static final String BASE="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String getShortURL(long code) {
        StringBuilder ans=new StringBuilder();
        if (code == 0) {
            return "0";
        }
        while (code >0){
            ans.append(BASE.charAt((int)(code%62)));
            code=code/62;
        }
        return ans.reverse().toString();
    }
}
