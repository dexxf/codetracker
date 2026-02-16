package com.io.codetracker.domain.classroom.result;

public enum ClassroomCreationResult {

    CLASSROOM_CREATION_FAILED("Failed to create classroom."),
    INVALID_INSTRUCTOR("Instructor ID is invalid."),
    USERID_NOT_FOUND("Instructor user ID was not found."),
    INVALID_NAME("Classroom name is invalid."),
    INVALID_DESCRIPTION("Classroom description is invalid."),
    SETTING_CREATION_FAILED("Failed to create classroom settings."),
    INVALID_MAX_STUDENTS("Max students value is invalid.");

    private final String message;

    ClassroomCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
