package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.GithubOAuthLoginCommand;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;
import com.io.codetracker.application.auth.result.GithubOAuthLoginData;
import com.io.codetracker.common.result.Result;

public interface GithubOAuthLoginUseCase {
    Result<GithubOAuthLoginData, GithubOAuthLoginError> loginOrRegister(GithubOAuthLoginCommand command);
}