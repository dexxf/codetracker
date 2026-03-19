package com.io.codetracker.adapter.classroom.in.mapper;


import org.springframework.http.HttpStatus;

import com.io.codetracker.application.classroom.error.CloseClassroomError;

public final class CloseClassroomHttpMapper {
    
    public static HttpStatus toStatus(CloseClassroomError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NOT_INSTRUCTOR -> HttpStatus.FORBIDDEN;
            case ALREADY_CLOSED -> HttpStatus.BAD_REQUEST;
            case CLASSROOM_SETTINGS_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(CloseClassroomError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
            case NOT_INSTRUCTOR -> "Only the instructor can close the classroom";
            case ALREADY_CLOSED -> "Classroom is already closed";
            case CLASSROOM_SETTINGS_NOT_FOUND -> "Classroom settings not found";
            default -> "An unknown error occurred";
        };
    }

}

