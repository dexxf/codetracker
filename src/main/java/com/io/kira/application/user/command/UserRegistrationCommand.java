package com.io.kira.application.user.command;


import org.springframework.web.multipart.MultipartFile;

public record UserRegistrationCommand(
        String firstName,
        String lastName,
        String gender,
        MultipartFile profile
) {
}
