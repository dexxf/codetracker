package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.GithubRegistrationCommand;
import com.io.codetracker.application.auth.error.GithubAccountRegistrationError;
import com.io.codetracker.application.auth.result.GithubAccountAttributes;
import com.io.codetracker.common.result.Result;

public interface GithubAccountRegistrationUseCase {
    Result<GithubAccountAttributes, GithubAccountRegistrationError> registerGithubAccount(GithubRegistrationCommand command);
}
