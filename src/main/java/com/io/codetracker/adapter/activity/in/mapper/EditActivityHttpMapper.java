package com.io.codetracker.adapter.activity.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.activity.error.EditActivityError;

public final class EditActivityHttpMapper {

    private EditActivityHttpMapper() {}

    public static HttpStatus toStatus(EditActivityError error) {
        return switch (error) {
            case ACTIVITY_NOT_FOUND,
                 UNKNOWN_CLASSROOM-> HttpStatus.NOT_FOUND;
            case ACTIVITY_ARCHIVED -> HttpStatus.CONFLICT;
            case NOT_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(EditActivityError error) {
        return switch (error) {
            case ACTIVITY_NOT_FOUND -> "Activity not found.";
            case TITLE_EMPTY -> "Title cannot be empty or too short/long.";
            case DESCRIPTION_EMPTY -> "Description cannot be empty or exceed maximum length.";
            case MAX_SCORE_INVALID -> "Max score is invalid.";
            case DUE_DATE_INVALID -> "Due date cannot be in the past.";
            case ACTIVITY_ARCHIVED -> "Activity is already archived and cannot be edited.";
            case INVALID_STATUS_TRANSITION -> "Invalid status transition.";
            case INVALID_DESCRIPTION -> "Description is invalid.";
            case UNKNOWN_CLASSROOM -> "Classroom does not exists";
            case NOT_INSTRUCTOR -> "User is not the owner of classroom";
        };
    }
}