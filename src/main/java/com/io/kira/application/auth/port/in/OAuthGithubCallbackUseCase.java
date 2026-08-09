package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.error.OAuthGithubCallbackError;
import com.io.kira.application.auth.result.OAuthGithubCallbackResult;
import com.io.kira.common.result.Result;

public interface OAuthGithubCallbackUseCase {

    Result<OAuthGithubCallbackResult, OAuthGithubCallbackError> handle(String code);
}