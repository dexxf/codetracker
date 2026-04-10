package com.io.codetracker.adapter.classroom.in.mapper;

import com.io.codetracker.application.classroom.error.GetClassroomRecentActivitiesError;
import org.springframework.http.HttpStatus;

public final class GetClassroomRecentActivitiesHttpMapper {

    private GetClassroomRecentActivitiesHttpMapper() {
    }

    public static HttpStatus toStatus(GetClassroomRecentActivitiesError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_MEMBER -> HttpStatus.UNAUTHORIZED;
        };
    }

    public static String toMessage(GetClassroomRecentActivitiesError error) {
        return switch (error) {
            case CLASSROOM_NOT_FOUND -> "Classroom not found";
            case USER_NOT_CLASSROOM_MEMBER -> "User is not part of this classroom";
        };
    }
}
