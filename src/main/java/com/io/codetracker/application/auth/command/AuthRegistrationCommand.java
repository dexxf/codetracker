package com.io.codetracker.application.auth.command;

import jakarta.validation.constraints.NotBlank;

public record AuthRegistrationCommand(@NotBlank String email,@NotBlank String username,@NotBlank String rawPassword, String role) {
}