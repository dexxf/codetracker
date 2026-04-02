package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.error.GetClassroomStudentActivityError;
import org.springframework.http.HttpStatus;

public final class GetActivityHttpMapper {

    private GetActivityHttpMapper () {}

    public static HttpStatus ownerToStatus(GetClassroomOwnerActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String ownerToMessage(GetClassroomOwnerActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom does not exists";
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
        };
    }

    public static HttpStatus studentToStatus(GetClassroomStudentActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_STUDENT -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String studentToMessage(GetClassroomStudentActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom does not exists";
            case USER_NOT_CLASSROOM_STUDENT -> "User is not a student of this classroom";
        };
    }

}
