package com.io.codetracker.application.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserProfileCommand (@NotBlank String userId, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String gender,@NotBlank String phoneNumber,
                                  @NotBlank String bio,@NotNull LocalDate birthday) {
}
