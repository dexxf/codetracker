package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import org.springframework.http.HttpStatus;

public final class GetActivityHttpMapper {

    private GetActivityHttpMapper () {}

    public static HttpStatus toStatus(GetClassroomOwnerActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(GetClassroomOwnerActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom does not exists";
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
        };
    }

}
