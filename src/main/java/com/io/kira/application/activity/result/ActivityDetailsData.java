package com.io.kira.application.activity.result;


import java.util.UUID;
import com.io.kira.domain.activity.entity.Activity;
import com.io.kira.domain.activity.valueObject.ActivityStatus;

import java.time.Instant;

public record ActivityDetailsData(UUID activityId, UUID classroomId, UUID instructorUserId, String title, String description,
                           Instant dueDate, ActivityStatus status, Integer maxScore, Instant createdAt, Instant updatedAt) {

    public static ActivityDetailsData from(Activity activity) {
        return new ActivityDetailsData(activity.getActivityId(), activity.getClassroomId(), activity.getInstructorUserId(),
                activity.getTitle(), activity.getDescription(), activity.getDueDate(), activity.getStatus(),
                activity.getMaxScore(), activity.getCreatedAt(), activity.getUpdatedAt());
    }
}

