package com.io.codetracker.application.auth.dto;

import com.io.codetracker.domain.auth.entity.Auth;

import java.util.Map;

public record AuthRegistrationDTO(boolean success, Map<String, Object> data, String message) {
    public static AuthRegistrationDTO success(Auth auth) {
        Map<String, Object> dataset = Map.ofEntries(
          Map.entry("authId", auth.getAuthId()),
          Map.entry("userId", auth.getUserId()),
          Map.entry("email", auth.getEmail().getValue())
        );
        return new AuthRegistrationDTO(true, dataset, "Successfully created auth.");
    }

    public static AuthRegistrationDTO fail(String message) {
        return new AuthRegistrationDTO(false, Map.of(), message);
    }
}
