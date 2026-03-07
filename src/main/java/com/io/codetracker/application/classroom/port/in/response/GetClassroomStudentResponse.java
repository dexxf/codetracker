package com.io.codetracker.application.classroom.port.in.response;

import com.io.codetracker.application.classroom.result.ClassroomStudentData;

import java.util.List;

public record GetClassroomStudentResponse (boolean success,List<ClassroomStudentData> data, String message) {

    public static GetClassroomStudentResponse ok(List<ClassroomStudentData> data) {
        return new GetClassroomStudentResponse(true,data, "Successfully fetched classroom students.");
    }
    public static GetClassroomStudentResponse fail(String message) {
        return new GetClassroomStudentResponse(false,null, message);
    }
}
