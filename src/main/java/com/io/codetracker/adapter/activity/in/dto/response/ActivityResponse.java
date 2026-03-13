package com.io.codetracker.adapter.activity.in.dto.response;

import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.domain.activity.entity.Activity;

public record ActivityResponse(boolean success, ActivityData data, String message) {

    public static ActivityResponse success(Activity activity, String message) {
        return new ActivityResponse(true, ActivityData.from(activity), message);
    }

    public static ActivityResponse fail(String message) {
        return new ActivityResponse(false, null, message);
    }
}