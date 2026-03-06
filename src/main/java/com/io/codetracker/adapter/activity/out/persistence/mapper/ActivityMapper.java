package com.io.codetracker.adapter.activity.out.persistence.mapper;

import com.io.codetracker.domain.activity.entity.Activity;
import com.io.codetracker.infrastructure.activity.persistence.entity.ActivityEntity;

public class ActivityMapper {

    private ActivityMapper() {}

    public static ActivityEntity toEntity(Activity activity) {
        return new ActivityEntity(
                activity.getActivityId(),
                activity.getClassroomId(),
                activity.getInstructorUserId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getDueDate(),
                activity.getStatus(),
                activity.getMaxScore(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }

    public static Activity toDomain(ActivityEntity entity) {
        return new Activity(
                entity.getActivityId(),
                entity.getClassroomId(),
                entity.getCreatedByProfessorId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDueDate(),
                entity.getStatus(),
                entity.getMaxScore(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}