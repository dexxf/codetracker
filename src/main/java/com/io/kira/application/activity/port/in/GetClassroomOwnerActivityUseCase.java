package com.io.kira.application.activity.port.in;

import com.io.kira.application.activity.command.GetActivityCommand;
import com.io.kira.application.activity.error.GetClassroomOwnerActivityError;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface GetClassroomOwnerActivityUseCase {
    Result<List<ActivityDetailsData>, GetClassroomOwnerActivityError> getOwnerClassroomActivity(GetActivityCommand command);
}
