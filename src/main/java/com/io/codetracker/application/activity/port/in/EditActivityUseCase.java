package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.error.EditActivityError;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

public interface EditActivityUseCase {
    Result<ActivityData, EditActivityError> execute (EditActivityCommand command);
}
