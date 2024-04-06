package com.project.teamttt.api.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtils {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final String NICKNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    public boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public boolean isValidNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }
}
