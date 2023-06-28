package com.aruma.mock.authone.core.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Method to encode (hash) a password
    public static String encodePassword(String password) {
        String salt = BCrypt.gensalt(); // Generate a random salt
        String hashedPassword = BCrypt.hashpw(password, salt); // Hash the password
        return hashedPassword;
    }

    // Method to check if a password matches the encoded (hashed) password
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}