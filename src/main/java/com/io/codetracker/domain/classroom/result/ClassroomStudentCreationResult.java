package com.io.codetracker.domain.classroom.result;

public enum ClassroomStudentCreationResult {
    CLASSROOM_ID_INVALID("Classroom ID cannot be null or empty."),
    STUDENT_USER_ID_INVALID("Student User ID cannot be null or empty."),
    STATUS_INVALID("Student status cannot be null."),
    USER_ALREADY_IN_CLASSROOM("User already in classroom.");

    private final String message;

    ClassroomStudentCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}