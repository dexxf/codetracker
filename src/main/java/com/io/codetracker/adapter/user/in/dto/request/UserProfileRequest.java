package com.io.codetracker.adapter.user.in.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserProfileRequest (@NotBlank String firstName, @NotBlank String lastName, @NotBlank String gender,@NotBlank String phoneNumber,
                                String bio,@NotNull LocalDate birthday) {
    
}
