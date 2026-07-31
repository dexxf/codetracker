package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.ActivityDetailsData;

public record ActivityResponse(ActivityDetailsData data, String message) {

    public static ActivityResponse success(ActivityDetailsData data, String message) {
        return new ActivityResponse(data , message);
    }

    public static ActivityResponse fail(String message) {
        return new ActivityResponse(null, message);
    }
}