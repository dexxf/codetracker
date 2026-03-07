package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;

import java.time.LocalDateTime;

public record ActivityData(String activityId, String classroomId, String instructorUserId, String title, String description,
                           LocalDateTime dueDate, ActivityStatus status, Integer maxScore, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
