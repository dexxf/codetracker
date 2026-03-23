package com.io.codetracker.application.classroom.error;

import com.io.codetracker.domain.classroom.result.EditClassroomResult;

public enum EditClassroomError {
    CLASSROOM_NOT_FOUND,
    CLASSROOM_SETTINGS_NOT_FOUND,
    INVALID_NAME,
    INVALID_DESCRIPTION,
    INVALID_MAX_STUDENTS,
    MAX_STUDENTS_LESS_THAN_ENROLLED,
    CLASSROOM_CLOSED,
    NOT_INSTRUCTOR;

    public static EditClassroomError from(EditClassroomResult result) {
        return switch (result) {
            case CLASSROOM_NOT_FOUND -> CLASSROOM_NOT_FOUND;
            case INVALID_NAME -> INVALID_NAME;
            case INVALID_DESCRIPTION -> INVALID_DESCRIPTION;
            case CLASSROOM_CLOSED -> CLASSROOM_CLOSED;
        };
    }
}
