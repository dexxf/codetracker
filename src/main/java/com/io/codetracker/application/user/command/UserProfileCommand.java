package com.io.codetracker.application.user.command;

import java.time.LocalDate;

public record UserProfileCommand (String firstName, String lastName, String gender, String phoneNumber, String bio, LocalDate birthday) {
}
