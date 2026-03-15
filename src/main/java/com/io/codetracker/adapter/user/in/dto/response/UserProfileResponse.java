package com.io.codetracker.adapter.user.in.dto.response;

import com.io.codetracker.application.user.result.UserData;

import java.util.List;

public record UserProfileResponse(UserData data , List<String> errors, String message) {

    public static UserProfileResponse ok(UserData userData) {
        return new UserProfileResponse(
                userData,
                List.of(),
                "Successfully updated user profile.");
    }

    public static UserProfileResponse fail(List<String> errors) {
        return new UserProfileResponse(
                null,
                errors,
                "Failed to update user profile.");
    }
}
