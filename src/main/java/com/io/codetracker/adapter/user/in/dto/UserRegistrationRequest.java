package com.io.codetracker.adapter.user.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UserRegistrationRequest(
        @NotBlank 
        String firstName,
        @NotBlank String lastName,
        @NotBlank String phoneNumber,
        @NotBlank String gender,
        @NotNull LocalDate birthday,
        String bio
) {}
 