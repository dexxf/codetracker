package com.io.codetracker.adapter.user.in.mapper;

import org.springframework.http.HttpStatus;

import com.io.codetracker.application.user.result.ProfilePictureResult;

public final class ProfilePictureHttpMapper {

    private ProfilePictureHttpMapper() {}

    public static HttpStatus toStatus(ProfilePictureResult result) {
        return switch (result) {
            case SUCCESS -> HttpStatus.OK;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NO_PROFILE_PICTURE -> HttpStatus.BAD_REQUEST;
            case MULTIPLE_ROWS_AFFECTED, MODIFICATION_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toUpdateMessage(ProfilePictureResult result) {
        return switch (result) {
            case SUCCESS -> "Profile picture updated successfully.";
            case USER_NOT_FOUND -> "User not found.";
            case NO_PROFILE_PICTURE -> "No profile picture to update.";
            case MULTIPLE_ROWS_AFFECTED -> "Unexpected error: multiple rows affected.";
            case MODIFICATION_FAILED -> "Failed to update profile picture.";
        };
    }

    public static String toDeleteMessage(ProfilePictureResult result) {
        return switch (result) {
            case SUCCESS -> "Profile picture removed successfully.";
            case USER_NOT_FOUND -> "User not found.";
            case NO_PROFILE_PICTURE -> "No profile picture to delete.";
            case MULTIPLE_ROWS_AFFECTED -> "Unexpected error: multiple rows affected.";
            case MODIFICATION_FAILED -> "Failed to delete profile picture.";
        };
    }
}