package com.io.kira.application.activity.port.in;


import java.util.UUID;
import com.io.kira.application.activity.command.GetActivityCommand;
import com.io.kira.application.activity.error.GetClassroomOwnerActivityError;
import com.io.kira.application.activity.result.StudentActivitySummaryData;
import com.io.kira.common.result.Result;

import java.util.Map;

public interface GetStudentActivityInfoUseCase {
    Result<Map<UUID, StudentActivitySummaryData>, GetClassroomOwnerActivityError> execute(GetActivityCommand command);
}

