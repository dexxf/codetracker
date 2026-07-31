package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.SubmitExistingRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;

public interface SubmitExistingRepositoryUseCase {
    Result<StudentActivitySubmissionData, SubmitExistingRepositoryError> submitExisting(UUID authId, UUID userId, UUID classroomId, String activityId, String repositoryUrl);
}

