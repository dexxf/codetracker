package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.GithubOAuthSignInCommand;
import com.io.codetracker.application.auth.error.GithubOAuthSignInError;
import com.io.codetracker.application.auth.result.GithubOAuthSignInData;
import com.io.codetracker.common.result.Result;

public interface OAuthGithubSignInUseCase {
    Result<GithubOAuthSignInData, GithubOAuthSignInError> loginOrRegister(GithubOAuthSignInCommand command);
}