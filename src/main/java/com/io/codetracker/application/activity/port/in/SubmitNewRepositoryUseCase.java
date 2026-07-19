package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.SubmitNewRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface SubmitNewRepositoryUseCase {
    Result<StudentActivityData, SubmitNewRepositoryError> submitNew(String authId, UUID userId, String classroomId, String activityId, String repositoryName);
}

