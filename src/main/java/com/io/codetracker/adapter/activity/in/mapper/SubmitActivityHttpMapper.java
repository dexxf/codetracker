package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.SubmitActivityError;
import org.springframework.http.HttpStatus;

public final class SubmitActivityHttpMapper {

    private SubmitActivityHttpMapper() {}

    public static HttpStatus toStatus(SubmitActivityError error) {
        return switch (error) {
            case USER_NOT_FOUND,
                 ACTIVITY_NOT_FOUND,
                 CLASSROOM_NOT_FOUND,
                 REPOSITORY_SUBMISSION_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_STUDENT -> HttpStatus.UNAUTHORIZED;
            case ALREADY_SUBMITTED -> HttpStatus.CONFLICT;
            case SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(SubmitActivityError error) {
        return switch (error) {
            case USER_NOT_FOUND -> "User not found";
            case USER_NOT_CLASSROOM_STUDENT -> "User is not an active student of this classroom";
            case ACTIVITY_NOT_FOUND -> "Activity not found in this classroom";
            case REPOSITORY_SUBMISSION_NOT_FOUND -> "Repository submission not found. Submit a repository first";
            case ALREADY_SUBMITTED -> "Activity already submitted";
            case SAVE_FAILED -> "Failed to submit activity";
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
        };
    }
}