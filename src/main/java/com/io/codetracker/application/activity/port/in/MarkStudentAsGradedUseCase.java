package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.MarkStudentAsGradedError;
import com.io.codetracker.application.activity.result.StudentActivitySubmissionData;
import com.io.codetracker.common.result.Result;

public interface MarkStudentAsGradedUseCase {
    Result<StudentActivitySubmissionData, MarkStudentAsGradedError> grade(UUID instructorUserId, UUID classroomId, String activityId, UUID studentUserId, String feedback, Integer score);
}
