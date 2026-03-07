package com.io.codetracker.application.classroom.port.in.response;


import com.io.codetracker.application.classroom.result.CreateClassroomData;

public record CreateClassroomResponse(boolean success, CreateClassroomData data, String message) {

        public static CreateClassroomResponse ok(CreateClassroomData data) {
            return new CreateClassroomResponse(true, data, null);
        }
        
        public static CreateClassroomResponse fail(String message) {
            return new CreateClassroomResponse(false, null, message);
        }
}