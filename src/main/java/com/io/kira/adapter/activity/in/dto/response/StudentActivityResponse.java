package com.io.kira.adapter.activity.in.dto.response;

import com.io.kira.application.activity.result.StudentActivitySubmissionData;

public record StudentActivityResponse(StudentActivitySubmissionData data, String message) {

    public static StudentActivityResponse success(StudentActivitySubmissionData data, String message) {
        return new StudentActivityResponse(data, message);
    }

    public static StudentActivityResponse fail(String message) {
        return new StudentActivityResponse(null, message);
    }
}
