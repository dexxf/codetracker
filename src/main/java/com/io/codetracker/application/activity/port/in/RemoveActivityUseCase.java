package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.RemoveActivityError;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

public interface RemoveActivityUseCase {
    Result<ActivityData, RemoveActivityError> execute(String classroomId, String activityId, UUID userId);
}

