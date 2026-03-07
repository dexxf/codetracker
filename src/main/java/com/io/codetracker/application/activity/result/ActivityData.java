package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public record ActivityData(String activityId, String classroomId, String instructorUserId, String title, String description,
                           LocalDateTime dueDate, ActivityStatus status, Integer maxScore, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ActivityData from(Activity activity) {
        return new ActivityData(activity.getActivityId(), activity.getClassroomId(), activity.getInstructorUserId(),
                activity.getTitle(), activity.getDescription(), activity.getDueDate(), activity.getStatus(),
                activity.getMaxScore(), activity.getCreatedAt(), activity.getUpdatedAt());
    }
}
