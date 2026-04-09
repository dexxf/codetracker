package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.activity.entity.StudentActivity;

import java.util.Optional;

public interface StudentActivityAppRepository {
    boolean existsSubmission(String userId, String activityId);
    boolean existsByUserId(String userId);
    Optional<StudentActivity> findByUserIdAndActivityId(String userId, String activityId);
    StudentActivity save(StudentActivity studentActivity);
}
