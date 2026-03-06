package com.io.codetracker.domain.activity.result;

public enum ActivityCreationResult {
    INVALID_CLASSROOM_ID("Classroom ID is invalid"),
    INVALID_INSTRUCTOR_ID("Instructor ID is invalid"),
    INVALID_TITLE("Title is invalid"),
    INVALID_DESCRIPTION("Description is invalid"),
    INVALID_DUE_DATE("Due date cannot be in the past"),
    INVALID_MAX_SCORE("Max score is invalid"),
    INVALID_STATUS("Status is invalid"),
    ACTIVITY_ALREADY_EXISTS("Activity already exists"),
    INSTRUCTOR_NOT_FOUND("Instructor is not found.");

    private final String message;

    ActivityCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}