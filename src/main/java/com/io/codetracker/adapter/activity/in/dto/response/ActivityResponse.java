package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.ActivityData;

public record ActivityResponse(boolean success, ActivityData data, String message) {

    public static ActivityResponse success(ActivityData data, String message) {
        return new ActivityResponse(true,data , message);
    }

    public static ActivityResponse fail(String message) {
        return new ActivityResponse(false, null, message);
    }
}