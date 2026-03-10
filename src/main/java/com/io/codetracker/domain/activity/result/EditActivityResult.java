package com.io.codetracker.domain.activity.result;

public enum EditActivityResult {
    ACTIVITY_NOT_FOUND("Activity not found."),
    TITLE_EMPTY("Title cannot be empty or too short/long."),
    DESCRIPTION_EMPTY("Description cannot be empty or exceed maximum length."),
    MAX_SCORE_INVALID("Max score is invalid."),
    DUE_DATE_INVALID("Due date cannot be in the past."),
    ACTIVITY_ARCHIVED("Activity is already archived and cannot be edited."),
    INVALID_STATUS_TRANSITION("Invalid status transition."),
    INVALID_DESCRIPTION("Description is invalid");

    private final String message;

    EditActivityResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}