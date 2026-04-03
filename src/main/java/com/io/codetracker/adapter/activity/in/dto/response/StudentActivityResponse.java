package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.StudentActivityData;

public record StudentActivityResponse(StudentActivityData data, String message) {

    public static StudentActivityResponse success(StudentActivityData data, String message) {
        return new StudentActivityResponse(data, message);
    }

    public static StudentActivityResponse fail(String message) {
        return new StudentActivityResponse(null, message);
    }
}
