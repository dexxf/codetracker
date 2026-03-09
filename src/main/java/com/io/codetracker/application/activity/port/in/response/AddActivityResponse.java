package com.io.codetracker.application.activity.port.in.response;

import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.domain.activity.entity.Activity;

public record AddActivityResponse(boolean success, ActivityData data, String message) {

    public static AddActivityResponse success(Activity activity) {
        return new AddActivityResponse(true, ActivityData.from(activity), null);
    }

    public static AddActivityResponse fail(String message) {
        return new AddActivityResponse(false, null, message);
    }
}