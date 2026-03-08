package com.io.codetracker.application.user.port.in.response;

public record DeleteProfilePictureResponse(boolean success, String message) {

    public static DeleteProfilePictureResponse success(String message) {
        return new DeleteProfilePictureResponse(true, message);
    }

    public static DeleteProfilePictureResponse failure(String message) {
        return new DeleteProfilePictureResponse(false, message);
    }
}