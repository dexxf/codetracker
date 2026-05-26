package com.io.codetracker.adapter.classroom.in.mapper;

import com.io.codetracker.application.classroom.result.LeaveClassroomResult;
import org.springframework.http.HttpStatus;

public final class LeaveClassroomHttpMapper {

    private LeaveClassroomHttpMapper() {
    }

    public static HttpStatus toStatus(LeaveClassroomResult result) {
        return switch (result) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_IN_CLASSROOM -> HttpStatus.NOT_FOUND;
            case USER_ALREADY_LEFT -> HttpStatus.BAD_REQUEST;
            case SUCCESS -> HttpStatus.OK;
        };
    }

    public static String toMessage(LeaveClassroomResult result) {
        return switch (result) {
            case SUCCESS -> "Successfully left classroom.";
            case CLASSROOM_NOT_FOUND -> "Classroom not found.";
            case USER_NOT_IN_CLASSROOM -> "User is not part of this classroom.";
            case USER_ALREADY_LEFT -> "User has already left this classroom.";
        };
    }
}
