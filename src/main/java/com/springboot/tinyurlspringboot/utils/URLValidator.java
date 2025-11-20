package com.springboot.tinyurlspringboot.utils;

import java.net.URL;

public class URLValidator {
    public static String normalize(String url){
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "https://" + url;
        }
        return url;
    }
    public static boolean isValidURL(String input) {
        try {
            String url = normalize(input);
            URL parsed = new URL(url);
            String protocol = parsed.getProtocol();
            String host = parsed.getHost();

            if (host == null || !host.contains(".")) return false;

            return (protocol.equals("http") || protocol.equals("https"));
        } catch (Exception e) {
            return false;
        }
    }

}
