package com.io.kira.application.activity.port.out;


import java.util.UUID;
import com.io.kira.domain.activity.entity.StudentActivity;

import java.util.Optional;

public interface StudentActivityAppRepository {
    boolean existsSubmission(UUID userId, String activityId);
    boolean existsByUserId(UUID userId);
    Optional<StudentActivity> findByUserIdAndActivityId(UUID userId, String activityId);
    Optional<String> findRepositoryUrlByUserIdAndActivityId(UUID userId, String activityId);
    StudentActivity save(StudentActivity studentActivity);
}

