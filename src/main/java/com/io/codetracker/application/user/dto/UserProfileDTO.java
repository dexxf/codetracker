package com.io.codetracker.application.user.dto;

import com.io.codetracker.domain.user.entity.User;

import java.util.Map;

public record UserProfileDTO(boolean success,String message,Map<String, Object> userDetails ,Map<String, Object> errorList) {

    public static UserProfileDTO ok(User user) {
        return new UserProfileDTO(true, "Successfully updated user profile.",
                Map.ofEntries(
                        Map.entry("userId", user.getUserId()),
                        Map.entry("firstName", user.getFirstName()),
                        Map.entry("lastName", user.getLastName()),
                        Map.entry("gender", user.getGender()),
                        Map.entry("phoneNumber",user.getPhoneNumber().getValue()),
                        Map.entry("bio", user.getBio()),
                        Map.entry("birthday", user.getBirthday().getValue()),
                        Map.entry("profileUrl", user.getProfileUrl())
                ),
                Map.of());
    }

    public static UserProfileDTO fail(Map<String, Object> errors) {
        return new UserProfileDTO(false,"Failed to update user profile.",Map.of() ,errors);
    }

    public static UserProfileDTO fail(String message) {
        return new UserProfileDTO(false, message,Map.of() ,Map.of());
    }

}
