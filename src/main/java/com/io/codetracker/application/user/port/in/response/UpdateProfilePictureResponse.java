package com.io.codetracker.application.user.port.in.response;

public record UpdateProfilePictureResponse(boolean success, String message, String profileUrl) {

    public static UpdateProfilePictureResponse success(String profileUrl, String message) {
        return new UpdateProfilePictureResponse(true, message, profileUrl);
    }

    public static UpdateProfilePictureResponse failure(String message) {
        return new UpdateProfilePictureResponse(false, message, null);
    }
}