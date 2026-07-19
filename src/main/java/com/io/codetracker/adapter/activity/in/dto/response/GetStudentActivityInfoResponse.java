package com.io.codetracker.adapter.activity.in.dto.response;


import java.util.UUID;
import com.io.codetracker.application.activity.result.StudentActivityInfoUserData;

import java.util.Map;

public record GetStudentActivityInfoResponse(Map<UUID, StudentActivityInfoUserData> data, String error) {

    public static GetStudentActivityInfoResponse success(Map<UUID, StudentActivityInfoUserData> data) {
        return new GetStudentActivityInfoResponse(data, null);
    }

    public static GetStudentActivityInfoResponse fail(String error) {
        return new GetStudentActivityInfoResponse(null, error);
    }
}

