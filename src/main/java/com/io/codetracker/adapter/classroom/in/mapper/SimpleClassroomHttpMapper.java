package com.io.codetracker.adapter.classroom.in.mapper;


import org.springframework.http.HttpStatus;

import com.io.codetracker.application.classroom.error.SimpleClassroomError;

public final class SimpleClassroomHttpMapper {
    
    private SimpleClassroomHttpMapper(){}


    public static HttpStatus toStatus(SimpleClassroomError error) {
        return switch (error) {
            case NO_CLASSROOM_FOUND -> HttpStatus.NOT_FOUND;
            case USER_NOT_CLASSROOM_INSTRUCTOR -> HttpStatus.UNAUTHORIZED;
        };
    }

    public static String toMessage(SimpleClassroomError error) {
        return switch (error) {
            case NO_CLASSROOM_FOUND -> "Classroom not found";
            case USER_NOT_CLASSROOM_INSTRUCTOR -> "User is not the instructor of the classroom";
        };
    }
}
