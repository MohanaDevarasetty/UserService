package com.farmersbyte.enterprise.usermodule.onboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class Hashing {

    public String hashString(String content) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            final byte[] encodedhash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            log.error("Hashing failed ", e);
        }
        return null;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
