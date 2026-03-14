package com.io.codetracker.adapter.activity.in.mapper;

import com.io.codetracker.application.activity.error.RemoveActivityError;
import org.springframework.http.HttpStatus;

public final class RemoveActivityHttpMapper {

    private RemoveActivityHttpMapper() {}

    public static HttpStatus toStatus(RemoveActivityError error) {
        return switch (error) {
            case USER_NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
            case ACTIVITY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(RemoveActivityError error) {
        return switch(error) {
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the owner of this classroom";
            case ACTIVITY_NOT_FOUND -> "Activity not found";
        };
    }
}
