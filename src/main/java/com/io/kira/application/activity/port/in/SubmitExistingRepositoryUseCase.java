package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.error.SubmitExistingRepositoryError;
import com.io.kira.application.activity.result.StudentActivitySubmissionData;
import com.io.kira.common.result.Result;

public interface SubmitExistingRepositoryUseCase {
    Result<StudentActivitySubmissionData, SubmitExistingRepositoryError> submitExisting(UUID authId, UUID userId, UUID classroomId, UUID activityId, String repositoryUrl);
}

