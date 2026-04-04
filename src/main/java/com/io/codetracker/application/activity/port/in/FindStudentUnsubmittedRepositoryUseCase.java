package com.io.codetracker.application.activity.port.in;

import com.io.codetracker.application.activity.command.FindUnsubmittedRepositoryCommand;
import com.io.codetracker.application.activity.error.FindStudentUnsubmittedRepositoryError;
import com.io.codetracker.application.activity.result.ActivityData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface FindStudentUnsubmittedRepositoryUseCase {
    Result<List<ActivityData>, FindStudentUnsubmittedRepositoryError> execute(FindUnsubmittedRepositoryCommand command);
}
