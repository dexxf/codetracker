package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.SubmitNewRepositoryError;
import org.springframework.http.HttpStatus;

public final class SubmitNewRepositoryHttpMapper {

    private SubmitNewRepositoryHttpMapper() {}

    public static HttpStatus toStatus(SubmitNewRepositoryError error) {
        return switch (error) {
            case USER_NOT_FOUND,
                 ACTIVITY_NOT_FOUND,
                 GITHUB_ACCOUNT_NOT_FOUND,
                 CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_STUDENT -> HttpStatus.UNAUTHORIZED;
            case ALREADY_SUBMITTED,
                 REPOSITORY_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case REPOSITORY_CREATE_FAILED,
                 SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(SubmitNewRepositoryError error) {
        return switch (error) {
            case USER_NOT_FOUND -> "User not found";
            case USER_NOT_CLASSROOM_STUDENT -> "User is not an active student of this classroom";
            case ACTIVITY_NOT_FOUND -> "Activity not found in this classroom";
            case ALREADY_SUBMITTED -> "Activity already submitted";
            case GITHUB_ACCOUNT_NOT_FOUND -> "Github account not found";
            case REPOSITORY_ALREADY_EXISTS -> "Repository already exists. Use /submit/existing if you are submitting an existing repository";
            case REPOSITORY_CREATE_FAILED -> "Failed to create repository. For /submit/new, send a new repository name or make sure your GitHub token can create repositories";
            case SAVE_FAILED -> "Failed to save submission";
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
        };
    }
}
