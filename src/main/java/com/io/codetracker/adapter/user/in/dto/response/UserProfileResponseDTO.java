package com.io.codetracker.adapter.user.in.dto.response;

import com.io.codetracker.application.user.result.UserData;

import java.util.List;

public record UserProfileResponseDTO(UserData data , List<String> errors, String message) {

    public static UserProfileResponseDTO ok(UserData userData) {
        return new UserProfileResponseDTO(
                userData,
                List.of(),
                "Successfully updated user profile.");
    }

    public static UserProfileResponseDTO fail(List<String> errors) {
        return new UserProfileResponseDTO(
                null,
                errors,
                "Failed to update user profile.");
    }
}
