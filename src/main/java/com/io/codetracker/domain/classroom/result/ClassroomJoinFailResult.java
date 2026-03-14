package com.io.codetracker.domain.classroom.result;

public enum ClassroomJoinFailResult {
    CODE_NOT_FOUND,
    CLASSROOM_NOT_ACTIVE,
    CANNOT_JOIN_OWN_CLASSROOM,
    SETTINGS_NOT_FOUND,
    PASSCODE_REQUIRED,
    WRONG_PASSCODE,
    USER_ALREADY_IN_CLASSROOM,
    CLASSROOM_FULL
}