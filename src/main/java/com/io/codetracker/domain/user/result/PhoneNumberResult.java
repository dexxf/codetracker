package com.io.codetracker.domain.user.result;

public enum PhoneNumberResult {
    INVALID_PHONE_NUMBER_FORMAT("Please enter a valid phone number."),
    PHONE_NUMBER_EMPTY("Phone number cannot be empty.");

    private final String message;

    PhoneNumberResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
