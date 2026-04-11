package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.error.MarkStudentAsGradedError;
import com.io.codetracker.application.activity.result.StudentActivityData;
import com.io.codetracker.common.result.Result;

public interface MarkStudentAsGradedUseCase {
    Result<StudentActivityData, MarkStudentAsGradedError> grade(String instructorUserId, String classroomId, String activityId, String studentUserId, String feedback, Integer score);
}