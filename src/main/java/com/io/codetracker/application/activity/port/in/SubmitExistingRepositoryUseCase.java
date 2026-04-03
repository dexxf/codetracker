package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.error.SubmitExistingRepositoryError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface SubmitExistingRepositoryUseCase {
    Result<StudentActivityData, SubmitExistingRepositoryError> submitExisting(String authId, String userId, String classroomId, String activityId, String repositoryUrl);
}
