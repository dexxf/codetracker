package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.SubmitExistingRepositoryError;
import org.springframework.http.HttpStatus;

public final class SubmitExistingRepositoryHttpMapper {

    private SubmitExistingRepositoryHttpMapper() {}

    public static HttpStatus toStatus(SubmitExistingRepositoryError error) {
        return switch (error) {
            case USER_NOT_FOUND,
                 ACTIVITY_NOT_FOUND,
                 GITHUB_ACCOUNT_NOT_FOUND,
                 CLASSROOM_NOT_FOUND,
                 REPOSITORY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_STUDENT -> HttpStatus.UNAUTHORIZED;
            case ALREADY_SUBMITTED -> HttpStatus.CONFLICT;
            case SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(SubmitExistingRepositoryError error) {
        return switch (error) {
            case USER_NOT_FOUND -> "User not found";
            case USER_NOT_CLASSROOM_STUDENT -> "User is not an active student of this classroom";
            case ACTIVITY_NOT_FOUND -> "Activity not found in this classroom";
            case ALREADY_SUBMITTED -> "Activity already submitted";
            case GITHUB_ACCOUNT_NOT_FOUND -> "Github account not found";
            case REPOSITORY_NOT_FOUND -> "Repository not found";
            case SAVE_FAILED -> "Failed to save submission";
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
        };
    }
}
