package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.error.MarkStudentAsGradedError;
import com.io.kira.application.activity.result.StudentActivitySubmissionData;
import com.io.kira.common.result.Result;

public interface MarkStudentAsGradedUseCase {
    Result<StudentActivitySubmissionData, MarkStudentAsGradedError> grade(UUID instructorUserId, UUID classroomId, String activityId, UUID studentUserId, String feedback, Integer score);
}
