package com.io.codetracker.application.auth.response;

import com.io.codetracker.domain.auth.entity.Auth;

import java.util.Map;

public record AuthRegistrationResponseDTO(boolean success, Map<String, Object> data, String message) {
    public static AuthRegistrationResponseDTO success(Auth auth) {
        Map<String, Object> dataset = Map.ofEntries(
          Map.entry("authId", auth.getAuthId()),
          Map.entry("userId", auth.getUserId()),
          Map.entry("email", auth.getEmail().getValue())
        );
        return new AuthRegistrationResponseDTO(true, dataset, "Successfully created auth.");
    }

    public static AuthRegistrationResponseDTO fail(String message) {
        return new AuthRegistrationResponseDTO(false, Map.of(), message);
    }
}
