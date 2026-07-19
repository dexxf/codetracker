package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.error.OAuthGithubCallbackError;
import com.io.codetracker.application.auth.result.OAuthGithubCallbackResult;
import com.io.codetracker.common.result.Result;

public interface OAuthGithubCallbackUseCase {

    Result<OAuthGithubCallbackResult, OAuthGithubCallbackError> handle(String code);
}