package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.MarkStudentAsGradedError;
import org.springframework.http.HttpStatus;

public final class MarkStudentAsGradedHttpMapper {

    private MarkStudentAsGradedHttpMapper() {}

    public static HttpStatus toStatus(MarkStudentAsGradedError error) {
        return switch (error) {
            case ACTIVITY_NOT_FOUND,
                 CLASSROOM_NOT_FOUND,
                 STUDENT_NOT_FOUND,
                 REPOSITORY_SUBMISSION_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_INSTRUCTOR,
                 STUDENT_NOT_CLASSROOM_STUDENT -> HttpStatus.UNAUTHORIZED;
            case ACTIVITY_NOT_SUBMITTED,
                 ALREADY_GRADED -> HttpStatus.CONFLICT;
              case INVALID_SCORE -> HttpStatus.BAD_REQUEST;
            case SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(MarkStudentAsGradedError error) {
        return switch (error) {
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
            case ACTIVITY_NOT_FOUND -> "Activity not found in this classroom";
            case STUDENT_NOT_FOUND -> "Student not found";
            case STUDENT_NOT_CLASSROOM_STUDENT -> "Student is not an active student of this classroom";
            case REPOSITORY_SUBMISSION_NOT_FOUND -> "Repository submission not found. Submit a repository first";
            case ACTIVITY_NOT_SUBMITTED -> "Only submitted activity can be graded";
            case INVALID_SCORE -> "Score must be a non-negative number";
            case ALREADY_GRADED -> "Activity already graded";
            case SAVE_FAILED -> "Failed to grade activity";
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
        };
    }
}