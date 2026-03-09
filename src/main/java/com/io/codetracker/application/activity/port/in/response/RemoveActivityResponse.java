package com.io.codetracker.application.activity.port.in.response;

import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.domain.activity.entity.Activity;

public record RemoveActivityResponse (boolean success, ActivityData data, String message){

    public static RemoveActivityResponse success(Activity activity) {
        return new RemoveActivityResponse(true, ActivityData.from(activity), "Activity removed successfully.");
    }

    public static RemoveActivityResponse fail(String message) {
        return new RemoveActivityResponse(false, null, message);
    }

}