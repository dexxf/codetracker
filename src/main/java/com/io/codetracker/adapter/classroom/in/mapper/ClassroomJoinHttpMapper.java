package com.io.codetracker.adapter.classroom.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.classroom.error.ClassroomJoinError;

public final class ClassroomJoinHttpMapper {

    private ClassroomJoinHttpMapper() {}

    public static HttpStatus toStatus(ClassroomJoinError error) {
        return switch (error) {
            case CODE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(ClassroomJoinError error) {
        return switch (error) {
            case CODE_NOT_FOUND -> "Classroom code not found.";
            case CLASSROOM_NOT_ACTIVE -> "Classroom is not active.";
            case CANNOT_JOIN_OWN_CLASSROOM -> "You cannot join your own classroom.";
            case SETTINGS_NOT_FOUND -> "Classroom settings not found.";
            case PASSCODE_REQUIRED -> "Classroom requires a passcode.";
            case WRONG_PASSCODE -> "Incorrect passcode.";
            case CLASSROOM_FULL -> "Classroom is already full.";
            case CLASSROOM_ID_INVALID -> "Invalid classroom ID.";
            case STUDENT_USER_ID_INVALID -> "Invalid student user ID.";
            case STATUS_INVALID -> "Invalid student status.";
            case USER_ALREADY_IN_CLASSROOM -> "User is already in the classroom.";
        };
    }
}