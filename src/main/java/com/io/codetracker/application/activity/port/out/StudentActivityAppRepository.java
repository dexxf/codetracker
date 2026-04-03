package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.activity.entity.StudentActivity;

public interface StudentActivityAppRepository {
    boolean existsSubmission(String userId, String activityId);
    boolean existsByUserId(String userId);
    StudentActivity save(StudentActivity studentActivity);
}
