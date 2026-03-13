package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

public interface RemoveActivityUseCase {
    Result<ActivityData, String> execute(String classroomId, String activityId, String userId);
}
