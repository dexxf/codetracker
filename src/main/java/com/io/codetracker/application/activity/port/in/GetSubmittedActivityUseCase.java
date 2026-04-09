package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.result.SubmittedActivityUserData;
import com.io.codetracker.common.result.Result;

import java.util.Map;

public interface GetSubmittedActivityUseCase {
    Result<Map<String, SubmittedActivityUserData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command);
}
