package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.AddActivityCommand;
import com.io.codetracker.application.activity.error.AddActivityError;
import com.io.codetracker.application.activity.result.ActivityDetailsData;
import com.io.codetracker.common.result.Result;

public interface AddActivityUseCase {
    Result<ActivityDetailsData, AddActivityError> execute(AddActivityCommand command);
}
