package com.io.codetracker.application.user.port.in.response;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;

import java.time.LocalDateTime;

public record FetchProfileDataResponse(
        String userId,
        String firstName,
        String lastName,
        Gender gender,
        PhoneNumber phoneNumber,
        String profileUrl,
        String bio,
        Birthday birthday,
        boolean hasFullyInitialized,
        LocalDateTime createdAt
) {
    public static FetchProfileDataResponse fromUser(User user) {
        return new FetchProfileDataResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getProfileUrl(),
                user.getBio(),
                user.getBirthday(),
                user.isHasFullyInitialized(),
                user.getCreatedAt()
        );
    }
}