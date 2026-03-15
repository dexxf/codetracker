package com.io.codetracker.adapter.user.in.dto.response;

public record DeleteProfilePictureResponse(String message) {

    public static DeleteProfilePictureResponse success(String message) {
        return new DeleteProfilePictureResponse(message);
    }

    public static DeleteProfilePictureResponse failure(String message) {
        return new DeleteProfilePictureResponse(message);
    }
}