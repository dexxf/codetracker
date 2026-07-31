package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.ActivityDetailsData;

import java.util.List;

public record GetActivityResponse(List<ActivityDetailsData> data, String error) {

    public static GetActivityResponse success(List<ActivityDetailsData> data) {
        return new GetActivityResponse(data, null);
    }

    public static GetActivityResponse fail(String error) {
        return new GetActivityResponse(null, error);
    }
}