package com.io.codetracker.adapter.classroom.in.dto.response;


import com.io.codetracker.application.classroom.result.CreateClassroomData;

public record CreateClassroomResponse(CreateClassroomData data, String message) {

        public static CreateClassroomResponse ok(CreateClassroomData data) {
            return new CreateClassroomResponse(data, null);
        }
        
        public static CreateClassroomResponse fail(String message) {
            return new CreateClassroomResponse(null, message);
        }
}