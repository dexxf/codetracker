package com.io.codetracker.application.user.response;

import com.io.codetracker.domain.user.entity.User;

import java.util.Map;

public record UserProfileResponseDTO(boolean success, String message, Map<String, Object> userDetails , Map<String, Object> errorList) {

    public static UserProfileResponseDTO ok(User user) {
        return new UserProfileResponseDTO(true, "Successfully updated user profile.",
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

    public static UserProfileResponseDTO fail(Map<String, Object> errors) {
        return new UserProfileResponseDTO(false,"Failed to update user profile.",Map.of() ,errors);
    }

    public static UserProfileResponseDTO fail(String message) {
        return new UserProfileResponseDTO(false, message,Map.of() ,Map.of());
    }

}
