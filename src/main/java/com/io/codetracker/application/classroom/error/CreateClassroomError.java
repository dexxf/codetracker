package com.io.codetracker.application.classroom.error;

import com.io.codetracker.domain.classroom.result.ClassroomCreationResult;

public enum CreateClassroomError {
    CLASSROOM_CREATION_FAILED,
    INVALID_INSTRUCTOR,
    USERID_NOT_FOUND,
    INVALID_NAME,
    INVALID_DESCRIPTION,
    SETTING_CREATION_FAILED,
    INVALID_MAX_STUDENTS,
    INVALID_PASSCODE;

    public static CreateClassroomError from(ClassroomCreationResult result) {
        return switch (result) {
            case CLASSROOM_CREATION_FAILED -> CLASSROOM_CREATION_FAILED;
            case INVALID_INSTRUCTOR -> INVALID_INSTRUCTOR;
            case USERID_NOT_FOUND -> USERID_NOT_FOUND;
            case INVALID_NAME -> INVALID_NAME;
            case INVALID_DESCRIPTION -> INVALID_DESCRIPTION;
            case SETTING_CREATION_FAILED -> SETTING_CREATION_FAILED;
            case INVALID_MAX_STUDENTS -> INVALID_MAX_STUDENTS;
            case INVALID_PASSCODE -> INVALID_PASSCODE;
        };
        
    }
}
