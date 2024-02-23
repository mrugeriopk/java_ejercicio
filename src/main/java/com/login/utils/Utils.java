package com.login.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
    public String encripta(String pass) throws Exception {
        MessageDigest encrip = null;
        encrip = MessageDigest.getInstance("SHA-256");
        byte[] hash = encrip.digest(pass.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }
}
