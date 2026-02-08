package com.io.codetracker.application.auth.response;

import java.util.Map;
import com.io.codetracker.application.auth.result.GithubAccountAttributes;

public record GithubRegistrationResponseDTO(
        boolean success,
        String message,
        Map<GithubAccountAttributes, Object> data
) {

    public static GithubRegistrationResponseDTO success(Map<GithubAccountAttributes, Object> data) {
        return new GithubRegistrationResponseDTO(true, "GitHub account registered successfully", data);
    }

    public static GithubRegistrationResponseDTO failure(String message) {
        return new GithubRegistrationResponseDTO(false, message, Map.of());
    }
}
