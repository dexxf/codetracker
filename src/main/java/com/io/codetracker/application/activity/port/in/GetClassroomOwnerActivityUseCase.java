package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.result.ActivityDetailsData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetClassroomOwnerActivityUseCase {
    Result<List<ActivityDetailsData>, GetClassroomOwnerActivityError> getOwnerClassroomActivity(GetActivityCommand command);
}
