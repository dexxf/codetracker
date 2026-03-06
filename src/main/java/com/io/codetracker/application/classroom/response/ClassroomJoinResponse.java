package com.io.codetracker.application.classroom.response;

import com.io.codetracker.application.classroom.result.ClassroomJoinResult;

public record ClassroomJoinResponse(
        boolean success,
        ClassroomJoinResult data,
        String error
) {

    public static ClassroomJoinResponse ok(ClassroomJoinResult result) {
        return new ClassroomJoinResponse(true, result, null);
    }

    public static ClassroomJoinResponse fail(String errorMessage) {
        return new ClassroomJoinResponse(false, null, errorMessage);
    }
}