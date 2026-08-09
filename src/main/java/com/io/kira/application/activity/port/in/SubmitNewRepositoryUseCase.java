package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.error.SubmitNewRepositoryError;
import com.io.kira.application.activity.result.StudentActivitySubmissionData;
import com.io.kira.common.result.Result;

public interface SubmitNewRepositoryUseCase {
    Result<StudentActivitySubmissionData, SubmitNewRepositoryError> submitNew(UUID authId, UUID userId, UUID classroomId, String activityId, String repositoryName);
}

