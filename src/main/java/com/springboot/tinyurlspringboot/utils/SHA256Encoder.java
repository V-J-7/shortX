package com.springboot.tinyurlspringboot.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA256Encoder {
    private static final String BASE="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String getSHA256Encoded(String originalURL) {
        try{
            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            byte[] hash=digest.digest(originalURL.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString=new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
    public static String getShortURL(String originalURL) {
        String SHAEncoded= SHA256Encoder.getSHA256Encoded(originalURL);
        StringBuilder ans=new StringBuilder();
        long shortenedSHA=Long.parseUnsignedLong(SHAEncoded.substring(0,15),16);
        while (shortenedSHA>0){
            ans.append(BASE.charAt((int)(shortenedSHA%62)));
            shortenedSHA=shortenedSHA/62;
        }
        return ans.reverse().toString();
    }
}
