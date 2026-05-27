package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record ActivityData(String activityId, String classroomId, String instructorUserId, String title, String description,
                           Instant dueDate, ActivityStatus status, Integer maxScore, Instant createdAt, Instant updatedAt) {

    public static ActivityData from(Activity activity) {
        return new ActivityData(activity.getActivityId(), activity.getClassroomId(), activity.getInstructorUserId(),
                activity.getTitle(), activity.getDescription(), activity.getDueDate(), activity.getStatus(),
                activity.getMaxScore(), activity.getCreatedAt(), activity.getUpdatedAt());
    }
}
