package com.VM.MockProject.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueRandomStringGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String uniqueRandomString(int length) {
        if (length > CHARACTERS.length()) {
            throw new IllegalArgumentException("Length cannot be greater than the number of unique characters available.");
        }

        StringBuilder sb = new StringBuilder(length);
        Set<Character> usedChars = new HashSet<>();

        while (sb.length() < length) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);

            if (!usedChars.contains(randomChar)) {
                sb.append(randomChar);
                usedChars.add(randomChar);
            }
        }

        return sb.toString();
    }
}
