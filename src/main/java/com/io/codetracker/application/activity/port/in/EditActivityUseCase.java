package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.EditActivityCommand;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

public interface EditActivityUseCase {
    Result<ActivityData, String> execute (EditActivityCommand command);
}
