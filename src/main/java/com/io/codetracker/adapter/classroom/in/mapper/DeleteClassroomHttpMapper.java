package com.io.codetracker.adapter.classroom.in.mapper;

import com.io.codetracker.application.classroom.result.DeleteClassroomResult;

import org.springframework.http.HttpStatus;

public final class DeleteClassroomHttpMapper {

    private DeleteClassroomHttpMapper() {
    }

    public static HttpStatus toStatus(DeleteClassroomResult error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case NOT_INSTRUCTOR -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(DeleteClassroomResult error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
            case NOT_INSTRUCTOR -> "Only the instructor can delete the classroom";
            default -> "Unknown error";
        };
    }
}