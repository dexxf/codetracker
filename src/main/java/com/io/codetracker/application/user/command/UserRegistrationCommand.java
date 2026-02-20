package com.io.codetracker.application.user.command;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public record UserRegistrationCommand(
        String firstName,
        String lastName,
        String phoneNumber,
        String gender,
        LocalDate birthday,
        MultipartFile profile,
        String bio
) {
}
