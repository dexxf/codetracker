package com.io.codetracker.application.activity.port.in.response;

import com.io.codetracker.application.activity.result.ActivityData;

import java.util.List;

public record GetActivityResponse(boolean success, List<ActivityData> data, String error) {

    public static GetActivityResponse success(List<ActivityData> data) {
        return new GetActivityResponse(true, data, null);
    }

    public static GetActivityResponse fail(String error) {
        return new GetActivityResponse(false, null, error);
    }
}