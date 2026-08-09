package com.io.kira.application.activity.port.in;

import com.io.kira.application.activity.command.FindUnsubmittedRepositoryCommand;
import com.io.kira.application.activity.error.FindStudentUnsubmittedRepositoryError;
import com.io.kira.application.activity.result.ActivityDetailsData;
import com.io.kira.common.result.Result;

import java.util.List;

public interface FindStudentUnsubmittedRepositoryUseCase {
    Result<List<ActivityDetailsData>, FindStudentUnsubmittedRepositoryError> execute(FindUnsubmittedRepositoryCommand command);
}
