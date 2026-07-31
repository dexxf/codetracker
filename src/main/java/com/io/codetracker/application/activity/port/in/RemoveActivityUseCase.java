package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.error.RemoveActivityError;
import com.io.codetracker.application.activity.result.ActivityDetailsData;
import com.io.codetracker.common.result.Result;

public interface RemoveActivityUseCase {
    Result<ActivityDetailsData, RemoveActivityError> execute(UUID classroomId, String activityId, UUID userId);
}

