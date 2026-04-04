package com.io.codetracker.adapter.activity.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.activity.error.FindStudentUnsubmittedRepositoryError;

public final class FindUnsubmittedRepositoryHttpMapper {

    private FindUnsubmittedRepositoryHttpMapper() {}

    public static HttpStatus toStatus(FindStudentUnsubmittedRepositoryError error) {
        return switch (error) {
            case ACTIVITY_NOT_FOUND,
                 CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_STUDENT -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(FindStudentUnsubmittedRepositoryError error) {
        return switch (error) {
            case ACTIVITY_NOT_FOUND -> "Activity not found";
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
            case USER_NOT_CLASSROOM_STUDENT -> "User is not a student in this classroom";
        };
    }
}