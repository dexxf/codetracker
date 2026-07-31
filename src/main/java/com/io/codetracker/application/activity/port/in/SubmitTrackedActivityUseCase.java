package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.SubmitActivityError;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;

public interface SubmitTrackedActivityUseCase {
    Result<StudentActivitySubmissionData, SubmitActivityError> submit(UUID userId, UUID classroomId, String activityId);
}
