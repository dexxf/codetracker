package com.io.codetracker.adapter.activity.out.persistence.mapper;

import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;

public class ActivityMapper {

    private ActivityMapper() {}

    public static ActivityEntity toEntity(Activity activity) {
        ActivityEntity entity = new ActivityEntity();
        entity.setActivityId(activity.getActivityId());
        entity.setTitle(activity.getTitle());
        entity.setDescription(activity.getDescription());
        entity.setDueDate(activity.getDueDate());
        entity.setStatus(activity.getStatus());
        entity.setMaxScore(activity.getMaxScore());
        entity.setCreatedAt(activity.getCreatedAt());
        entity.setUpdatedAt(activity.getUpdatedAt());
        return entity;
    }

    public static Activity toDomain(ActivityEntity entity) {
        return new Activity(
                entity.getActivityId(),
                entity.getClassroomEntity().getClassroomId(),
                entity.getClassroomEntity().getInstructorUserId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate(),
                entity.getStatus(),
                entity.getMaxScore(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static void updateEntity(Activity updatedActivity, ActivityEntity entity) {
        entity.setTitle(updatedActivity.getTitle());
        entity.setDescription(updatedActivity.getDescription());
        entity.setDueDate(updatedActivity.getDueDate());
        entity.setStatus(updatedActivity.getStatus());
        entity.setMaxScore(updatedActivity.getMaxScore());
        entity.setUpdatedAt(updatedActivity.getUpdatedAt());
    }
}