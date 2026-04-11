package com.io.codetracker.adapter.activity.out.persistence.mapper;

import com.io.codetracker.domain.activity.entity.StudentActivity;
import com.io.codetracker.infrastructure.activity.persistence.entity.StudentActivityEntity;

public class StudentActivityMapper {

    private StudentActivityMapper() {}

    public static StudentActivity toDomain(StudentActivityEntity entity) {
        if (entity == null) {
            return null;
        }

        return new StudentActivity(
                entity.getStudentActivityId().toString(), // TODO: convert Student activity Id attribute in the domain entity to UUID to stay consistent
                entity.getActivityEntity().getActivityId(),
                entity.getUserEntity().getUserId(),
                entity.getSubmissionStatus(),
            entity.getFeedback(),
            entity.getScore()
        );
    }
}