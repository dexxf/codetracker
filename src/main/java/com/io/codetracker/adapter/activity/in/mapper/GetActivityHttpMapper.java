package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.GetActivityError;
import org.springframework.http.HttpStatus;

public final class GetActivityHttpMapper {

    private GetActivityHttpMapper () {}

    public static HttpStatus toStatus(GetActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(GetActivityError error){
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom does not exists";
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
        };
    }

}
