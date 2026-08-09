package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.error.RemoveActivityError;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;

public interface RemoveActivityUseCase {
    Result<ActivityDetailsData, RemoveActivityError> execute(UUID classroomId, String activityId, UUID userId);
}

