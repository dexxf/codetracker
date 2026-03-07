package com.io.codetracker.application.activity.port.in.response;

import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.domain.activity.entity.Activity;

public record AddActivityResponse(boolean success, ActivityData data, String message) {

    public static AddActivityResponse success(Activity activity) {

        ActivityData data = new ActivityData(
                activity.getActivityId(), activity.getClassroomId(), activity.getInstructorUserId(), activity.getTitle(),
                activity.getDescription(), activity.getDueDate(), activity.getStatus(), activity.getMaxScore(), activity.getCreatedAt(), activity.getUpdatedAt()
        );

        return new AddActivityResponse(true, data, null);
    }


    public static AddActivityResponse fail(String message) {
        return new AddActivityResponse(false, null, message);
    }
}