package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.command.GithubOAuthSignInCommand;
import com.io.kira.application.auth.error.GithubOAuthSignInError;
import com.io.kira.application.auth.result.GithubOAuthSignInData;
import com.io.kira.common.result.Result;

public interface OAuthGithubSignInUseCase {
    Result<GithubOAuthSignInData, GithubOAuthSignInError> loginOrRegister(GithubOAuthSignInCommand command);
}