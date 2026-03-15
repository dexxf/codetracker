package com.io.codetracker.adapter.user.in.dto.response;

public record UpdateProfilePictureResponse(String message, String profileUrl) {

    public static UpdateProfilePictureResponse success(String profileUrl, String message) {
        return new UpdateProfilePictureResponse(message, profileUrl);
    }

    public static UpdateProfilePictureResponse failure(String message) {
        return new UpdateProfilePictureResponse(message, null);
    }
}