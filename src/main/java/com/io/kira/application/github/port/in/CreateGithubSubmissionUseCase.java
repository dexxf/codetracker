package com.io.kira.application.github.port.in;

import com.io.kira.application.github.command.CreateGithubSubmissionCommand;
import com.io.kira.application.github.error.CreateGithubSubmissionError;
import com.io.kira.application.github.result.GithubSubmissionData;
import com.io.kira.common.result.Result;

public interface CreateGithubSubmissionUseCase {
    Result<GithubSubmissionData, CreateGithubSubmissionError> execute(CreateGithubSubmissionCommand command);
}
