package com.io.codetracker.adapter.activity.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.activity.error.AddActivityError;

public final class AddActivityHttpMapper {

    private AddActivityHttpMapper() {}

    public static HttpStatus toStatus(AddActivityError error) {
        return switch (error) {
            case ACTIVITY_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INSTRUCTOR_NOT_FOUND,
                 UNKNOWN_CLASSROOM -> HttpStatus.NOT_FOUND;
            case SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            case NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(AddActivityError error) {
        return switch (error) {
            case INVALID_CLASSROOM_ID -> "Invalid classroom ID";
            case INVALID_INSTRUCTOR_ID -> "Invalid instructor ID";
            case INVALID_TITLE -> "Title is invalid";
            case INVALID_DESCRIPTION -> "Description is invalid";
            case INVALID_DUE_DATE -> "Due date cannot be in the past";
            case INVALID_MAX_SCORE -> "Max score is invalid";
            case INVALID_STATUS -> "Status is invalid";
            case ACTIVITY_ALREADY_EXISTS -> "Activity already exists";
            case INSTRUCTOR_NOT_FOUND -> "Instructor not found";
            case UNKNOWN_CLASSROOM -> "Classroom does not exists";
            case NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
            case SAVE_FAILED -> "Failed to save activity";
        };
    }
}