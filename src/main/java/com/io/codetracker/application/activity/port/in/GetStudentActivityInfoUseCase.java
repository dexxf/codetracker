package com.io.codetracker.application.activity.port.in;


import java.util.UUID;
import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetClassroomOwnerActivityError;
import com.io.codetracker.application.activity.result.StudentActivityInfoUserData;
import com.io.codetracker.common.result.Result;

import java.util.Map;

public interface GetStudentActivityInfoUseCase {
    Result<Map<UUID, StudentActivityInfoUserData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command);
}

