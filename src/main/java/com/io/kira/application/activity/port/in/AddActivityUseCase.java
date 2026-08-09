package com.io.kira.application.activity.port.in;

import com.io.kira.application.activity.command.AddActivityCommand;
import com.io.kira.application.activity.error.AddActivityError;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;

public interface AddActivityUseCase {
    Result<ActivityDetailsData, AddActivityError> execute(AddActivityCommand command);
}
