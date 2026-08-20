package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.error.SubmitActivityError;
import com.io.kira.application.activity.result.StudentActivitySubmissionData;
import com.io.kira.common.result.Result;

public interface SubmitTrackedActivityUseCase {
    Result<StudentActivitySubmissionData, SubmitActivityError> submit(UUID authId, UUID userId, UUID classroomId, UUID activityId);
}
