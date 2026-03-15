package com.io.codetracker.adapter.classroom.in.mapper;

import org.springframework.http.HttpStatus;

import com.io.codetracker.application.classroom.error.CreateClassroomError;

public final class CreateClassroomHttpMapper {

    private CreateClassroomHttpMapper() {}

    public static HttpStatus toStatus(CreateClassroomError error) {
        return switch (error) {
            case CLASSROOM_CREATION_FAILED, SETTING_CREATION_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            case USERID_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(CreateClassroomError error) {
        return switch (error) {
            case CLASSROOM_CREATION_FAILED -> "Failed to create classroom.";
            case INVALID_INSTRUCTOR -> "Invalid instructor.";
            case USERID_NOT_FOUND -> "User ID not found.";
            case INVALID_NAME -> "Invalid classroom name.";
            case INVALID_DESCRIPTION -> "Invalid classroom description.";
            case SETTING_CREATION_FAILED -> "Failed to create classroom settings.";
            case INVALID_MAX_STUDENTS -> "Invalid maximum number of students.";
            case INVALID_PASSCODE -> "Invalid classroom passcode.";
        };
    }
}