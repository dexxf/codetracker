package com.io.codetracker.domain.activity.result;

public enum EditActivityResult {
    ACTIVITY_NOT_FOUND,
    TITLE_EMPTY,
    DESCRIPTION_EMPTY,
    MAX_SCORE_INVALID,
    DUE_DATE_INVALID,
    ACTIVITY_ARCHIVED,
    INVALID_STATUS_TRANSITION,
    INVALID_DESCRIPTION;
}