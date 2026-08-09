package com.io.kira.adapter.user.in.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationRequest(
        @NotBlank 
        String firstName,
        @NotBlank String lastName,
        @NotBlank String gender
) {}
 