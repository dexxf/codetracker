package com.io.codetracker.application.user.result;

import com.io.codetracker.domain.user.entity.User;

import java.util.UUID;

public record UserData(
        UUID userId,
        String firstName,
        String lastName,
        String gender,
        String profileUrl,
        boolean hasFullyInitialized
) {

    public static UserData from(User user) {
        return new UserData(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender().name(),
                user.getProfileUrl(),
                user.getHasFullyInitialized());
    }
}

