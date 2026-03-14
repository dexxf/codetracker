package com.io.codetracker.application.classroom.error;

import com.io.codetracker.domain.classroom.result.ClassroomJoinFailResult;
import com.io.codetracker.domain.classroom.result.ClassroomStudentCreationResult;

public enum ClassroomJoinError {
    CODE_NOT_FOUND,
    CLASSROOM_NOT_ACTIVE,
    CANNOT_JOIN_OWN_CLASSROOM,
    SETTINGS_NOT_FOUND,
    PASSCODE_REQUIRED,
    WRONG_PASSCODE,
    CLASSROOM_FULL,
    CLASSROOM_ID_INVALID,
    STUDENT_USER_ID_INVALID,
    STATUS_INVALID,
    USER_ALREADY_IN_CLASSROOM;

    public static ClassroomJoinError from(ClassroomJoinFailResult result) {
        return switch (result) {
            case CODE_NOT_FOUND -> CODE_NOT_FOUND;
            case CLASSROOM_NOT_ACTIVE -> CLASSROOM_NOT_ACTIVE;
            case CANNOT_JOIN_OWN_CLASSROOM -> CANNOT_JOIN_OWN_CLASSROOM;
            case SETTINGS_NOT_FOUND -> SETTINGS_NOT_FOUND;
            case PASSCODE_REQUIRED -> PASSCODE_REQUIRED;
            case WRONG_PASSCODE -> WRONG_PASSCODE;
            case CLASSROOM_FULL -> CLASSROOM_FULL;
            case USER_ALREADY_IN_CLASSROOM -> USER_ALREADY_IN_CLASSROOM;
        };
    }

    public static ClassroomJoinError from(ClassroomStudentCreationResult result) {
        return switch (result) {
            case CLASSROOM_ID_INVALID -> CLASSROOM_ID_INVALID;
            case STUDENT_USER_ID_INVALID -> STUDENT_USER_ID_INVALID;
            case STATUS_INVALID -> STATUS_INVALID;
            case USER_ALREADY_IN_CLASSROOM -> USER_ALREADY_IN_CLASSROOM;
        };
    }
}