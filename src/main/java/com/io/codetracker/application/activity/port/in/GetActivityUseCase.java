package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.GetActivityCommand;
import com.io.codetracker.application.activity.error.GetActivityError;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetActivityUseCase {
    Result<List<ActivityData>, GetActivityError> execute(GetActivityCommand command);
}
