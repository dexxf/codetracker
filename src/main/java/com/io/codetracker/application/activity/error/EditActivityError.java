package com.io.codetracker.application.activity.error;

import com.io.codetracker.domain.activity.result.EditActivityResult;

public enum EditActivityError {
    ACTIVITY_NOT_FOUND,
    TITLE_EMPTY,
    DESCRIPTION_EMPTY,
    MAX_SCORE_INVALID,
    DUE_DATE_INVALID,
    ACTIVITY_ARCHIVED,
    INVALID_STATUS_TRANSITION,
    INVALID_DESCRIPTION,
    NOT_INSTRUCTOR,
    UNKNOWN_CLASSROOM;

    public static EditActivityError from(EditActivityResult result) {
        return switch (result) {
            case ACTIVITY_NOT_FOUND -> ACTIVITY_NOT_FOUND;
            case TITLE_EMPTY -> TITLE_EMPTY;
            case DESCRIPTION_EMPTY -> DESCRIPTION_EMPTY;
            case MAX_SCORE_INVALID -> MAX_SCORE_INVALID;
            case DUE_DATE_INVALID -> DUE_DATE_INVALID;
            case ACTIVITY_ARCHIVED -> ACTIVITY_ARCHIVED;
            case INVALID_STATUS_TRANSITION -> INVALID_STATUS_TRANSITION;
            case INVALID_DESCRIPTION -> INVALID_DESCRIPTION;
        };
    }
}