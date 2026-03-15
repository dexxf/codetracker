package com.io.codetracker.application.user.result;

import com.io.codetracker.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserData(
        String userId,
        String firstName,
        String lastName,
        String gender,
        String phoneNumber,
        LocalDate birthday,
        String bio,
        String profileUrl,
        boolean hasFullyInitialized,
        LocalDateTime createdAt
) {

    public static UserData from(User user) {
        return new UserData(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender().name(),
                user.getPhoneNumber().getValue(),
                user.getBirthday().getValue(),
                user.getBio(),
                user.getProfileUrl(),
                user.isHasFullyInitialized(),
                user.getCreatedAt());
    }
}
