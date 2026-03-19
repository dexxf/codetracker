package com.io.codetracker.adapter.classroom.in.mapper;

import com.io.codetracker.application.classroom.error.EditClassroomError;
import org.springframework.http.HttpStatus;

public final class EditClassroomHttpMapper {

    private EditClassroomHttpMapper() {
    }

    public static HttpStatus toStatus(EditClassroomError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND, CLASSROOM_SETTINGS_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NOT_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            case MAX_STUDENTS_LESS_THAN_ENROLLED -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(EditClassroomError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom not found.";
            case CLASSROOM_SETTINGS_NOT_FOUND -> "Classroom settings not found.";
            case INVALID_NAME -> "Classroom name is invalid.";
            case INVALID_DESCRIPTION -> "Classroom description is invalid.";
            case INVALID_MAX_STUDENTS -> "Invalid maximum number of students.";
            case MAX_STUDENTS_LESS_THAN_ENROLLED -> "Max students cannot be lower than current enrolled students.";
            case NOT_INSTRUCTOR -> "User is not the owner of classroom.";
        };
    }
}
