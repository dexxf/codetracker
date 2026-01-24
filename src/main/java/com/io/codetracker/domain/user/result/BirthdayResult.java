package com.io.codetracker.domain.user.result;

public enum BirthdayResult {
    TOO_YOUNG("You must be at least 13 years old to register."),
    TOO_OLD("Please enter a valid age."),
    BIRTHDAY_IN_FUTURE("Your birthday cannot be in the future.");

    private final String message;

    BirthdayResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
