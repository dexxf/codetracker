package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.SubmittedActivityUserData;

import java.util.Map;

public record GetSubmittedActivityResponse(Map<String, SubmittedActivityUserData> data, String error) {

    public static GetSubmittedActivityResponse success(Map<String, SubmittedActivityUserData> data) {
        return new GetSubmittedActivityResponse(data, null);
    }

    public static GetSubmittedActivityResponse fail(String error) {
        return new GetSubmittedActivityResponse(null, error);
    }
}
