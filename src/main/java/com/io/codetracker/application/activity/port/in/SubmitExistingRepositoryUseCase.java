package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.SubmitExistingRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface SubmitExistingRepositoryUseCase {
    Result<StudentActivityData, SubmitExistingRepositoryError> submitExisting(UUID authId, UUID userId, UUID classroomId, String activityId, String repositoryUrl);
}

