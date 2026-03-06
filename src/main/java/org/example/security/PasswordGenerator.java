package org.example.security;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+";

    private final SecureRandom random = new SecureRandom();

    public String generate(int length, boolean useUpper, boolean useNumbers, boolean useSymbols) {

        String chars = LOWER;

        if (useUpper) chars += UPPER;
        if (useNumbers) chars += NUMBERS;
        if (useSymbols) chars += SYMBOLS;

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    public String generateStrongPassword() {
        return generate(16, true, true, true);
    }
}