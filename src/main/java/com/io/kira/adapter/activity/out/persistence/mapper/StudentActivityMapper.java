package com.io.kira.adapter.activity.out.persistence.mapper;

import com.io.kira.domain.activity.entity.StudentActivity;
import com.io.kira.infrastructure.activity.persistence.entity.StudentActivityEntity;

public class StudentActivityMapper {

    private StudentActivityMapper() {}

    public static StudentActivity toDomain(StudentActivityEntity entity) {
        if (entity == null) {
            return null;
        }

        return StudentActivity.reconstitute(
                entity.getStudentActivityId(), // TODO: convert Student activity Id attribute in the domain entity to UUID to stay consistent
                entity.getActivityEntity().getActivityId(),
                entity.getUserEntity().getUserId(),
                entity.getSubmissionStatus(),
                entity.getFeedback(),
                entity.getScore(),
                entity.getSubmittedCommitSha()
        );
    }
}
