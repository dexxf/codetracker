package com.io.kira.application.activity.port.in;

import com.io.kira.application.activity.command.EditActivityCommand;
import com.io.kira.application.activity.error.EditActivityError;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;

public interface EditActivityUseCase {
    Result<ActivityDetailsData, EditActivityError> execute (EditActivityCommand command);
}
