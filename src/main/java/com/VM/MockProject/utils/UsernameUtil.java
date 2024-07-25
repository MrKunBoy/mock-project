package com.VM.MockProject.utils;

public class UsernameUtil {
    public static String standardizeUsername(String fullName) {
        return fullName.toLowerCase().replaceAll("\\s+", "_");
    }
}
