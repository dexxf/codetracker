package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.MarkStudentAsGradedError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface MarkStudentAsGradedUseCase {
    Result<StudentActivityData, MarkStudentAsGradedError> grade(UUID instructorUserId, String classroomId, String activityId, UUID studentUserId, String feedback, Integer score);
}
