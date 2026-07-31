package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.StudentActivityOverviewData;

import java.util.List;

public record GetStudentViewDataResponse(
        List<StudentActivityOverviewData> data,
        String error
) {
    public static GetStudentViewDataResponse success(List<StudentActivityOverviewData> data) {
        return new GetStudentViewDataResponse(data, null);
    }

    public static GetStudentViewDataResponse fail(String error) {
        return new GetStudentViewDataResponse(null, error);
    }
}
