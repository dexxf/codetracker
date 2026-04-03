package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.error.SubmitNewRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface SubmitNewRepositoryUseCase {
    Result<StudentActivityData, SubmitNewRepositoryError> submitNew(String authId, String userId, String classroomId, String activityId, String repositoryName);
}
