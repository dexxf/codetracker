package com.io.kira.adapter.classroom.in.mapper;

import org.springframework.http.HttpStatus;

import com.io.kira.application.classroom.error.CreateClassroomError;

public final class CreateClassroomHttpMapper {

    private CreateClassroomHttpMapper() {}

    public static HttpStatus toStatus(CreateClassroomError error) {
        return switch (error) {
            case USERID_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(CreateClassroomError error) {
        return switch (error) {
            case INVALID_INSTRUCTOR -> "Invalid instructor.";
            case USERID_NOT_FOUND -> "User ID not found.";
            case INVALID_NAME -> "Invalid classroom name.";
            case INVALID_DESCRIPTION -> "Invalid classroom description.";
            case INVALID_MAX_STUDENTS -> "Invalid maximum number of students.";
            case INVALID_PASSCODE -> "Invalid classroom passcode.";
        };
    }
}
