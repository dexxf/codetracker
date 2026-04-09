package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.error.SubmitActivityError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface SubmitActivityUseCase {
    Result<StudentActivityData, SubmitActivityError> submit(String userId, String classroomId, String activityId);
}