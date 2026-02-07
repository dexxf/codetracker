package com.io.codetracker.application.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRegistrationCommand(@NotBlank String userId, @NotBlank String firstName,@NotBlank String lastName,
        @NotBlank String phoneNumber,@NotBlank String gender,@NotNull LocalDate birthday,@NotBlank String profileUrl, String bio) {
}
