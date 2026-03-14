package com.io.codetracker.application.activity.error;

import com.io.codetracker.domain.activity.result.ActivityCreationResult;

public enum AddActivityError {
    INVALID_CLASSROOM_ID,
    INVALID_INSTRUCTOR_ID,
    INVALID_TITLE,
    INVALID_DESCRIPTION,
    INVALID_DUE_DATE,
    INVALID_MAX_SCORE,
    INVALID_STATUS,
    ACTIVITY_ALREADY_EXISTS,
    INSTRUCTOR_NOT_FOUND,
    SAVE_FAILED, UNKNOWN_CLASSROOM,
    NOT_CLASSROOM_INSTRUCTOR;

    public static AddActivityError from(ActivityCreationResult error) {
        return switch (error) {
            case INVALID_CLASSROOM_ID -> INVALID_CLASSROOM_ID;
            case INVALID_INSTRUCTOR_ID -> INVALID_INSTRUCTOR_ID;
            case INVALID_TITLE -> INVALID_TITLE;
            case INVALID_DESCRIPTION -> INVALID_DESCRIPTION;
            case INVALID_DUE_DATE -> INVALID_DUE_DATE;
            case INVALID_MAX_SCORE -> INVALID_MAX_SCORE;
            case INVALID_STATUS -> INVALID_STATUS;
            case ACTIVITY_ALREADY_EXISTS -> ACTIVITY_ALREADY_EXISTS;
            case INSTRUCTOR_NOT_FOUND -> INSTRUCTOR_NOT_FOUND;
        };
    }
}