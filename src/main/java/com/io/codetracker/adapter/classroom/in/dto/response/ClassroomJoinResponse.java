package com.io.codetracker.adapter.classroom.in.dto.response;

import com.io.codetracker.application.classroom.result.ClassroomJoinResult;

public record ClassroomJoinResponse(
        ClassroomJoinResult data,
        String error
) {

    public static ClassroomJoinResponse ok(ClassroomJoinResult result) {
        return new ClassroomJoinResponse(result, null);
    }

    public static ClassroomJoinResponse fail(String errorMessage) {
        return new ClassroomJoinResponse(null, errorMessage);
    }
}