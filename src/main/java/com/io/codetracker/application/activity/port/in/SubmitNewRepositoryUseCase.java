package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.SubmitNewRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;

public interface SubmitNewRepositoryUseCase {
    Result<StudentActivitySubmissionData, SubmitNewRepositoryError> submitNew(UUID authId, UUID userId, UUID classroomId, String activityId, String repositoryName);
}

