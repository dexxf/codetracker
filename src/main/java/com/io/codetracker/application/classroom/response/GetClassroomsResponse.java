package com.io.codetracker.application.classroom.response;

import java.util.List;

import com.io.codetracker.domain.classroom.entity.Classroom;

public record GetClassroomsResponse(List<Classroom> data, String message) {

    public static GetClassroomsResponse ok(List<Classroom> data) {
        return new GetClassroomsResponse(data, null);
    }
    
    public static GetClassroomsResponse fail(String message) {
        return new GetClassroomsResponse(null, message);
    }
    
}
