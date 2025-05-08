package com.HE180030.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodingPassword {
    public static String getEncryptedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
