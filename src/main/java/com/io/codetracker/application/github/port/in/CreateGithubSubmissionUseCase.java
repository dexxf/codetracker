package com.io.codetracker.application.github.port.in;

import com.io.codetracker.application.github.command.CreateGithubSubmissionCommand;
import com.io.codetracker.application.github.error.CreateGithubSubmissionError;
import com.io.codetracker.application.github.result.GithubSubmissionData;
import com.io.codetracker.common.result.Result;

public interface CreateGithubSubmissionUseCase {
    Result<GithubSubmissionData, CreateGithubSubmissionError> execute(CreateGithubSubmissionCommand command);
}
