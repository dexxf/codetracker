package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.error.OAuthGithubCallbackError;
import com.io.codetracker.application.auth.port.in.OAuthGithubCallbackUseCase;
import com.io.codetracker.application.auth.port.out.GithubExchangeCodePort;
import com.io.codetracker.application.auth.port.out.GithubFetchUserInfoPort;
import com.io.codetracker.application.auth.result.GithubExchangeCodeResult;
import com.io.codetracker.application.auth.result.GithubFetchUserInfoResult;
import com.io.codetracker.application.auth.result.OAuthGithubCallbackResult;
import com.io.codetracker.common.result.Result;
import org.springframework.stereotype.Service;

@Service
public class OAuthGithubCallbackService implements OAuthGithubCallbackUseCase {

    private final GithubExchangeCodePort githubExchangeCodePort;
    private final GithubFetchUserInfoPort githubFetchUserInfoPort;

    public OAuthGithubCallbackService(
            GithubExchangeCodePort githubExchangeCodePort,
            GithubFetchUserInfoPort githubFetchUserInfoPort
    ) {
        this.githubExchangeCodePort = githubExchangeCodePort;
        this.githubFetchUserInfoPort = githubFetchUserInfoPort;
    }

    @Override
    public Result<OAuthGithubCallbackResult, OAuthGithubCallbackError> handle(String code) {
        if (code == null || code.isBlank()) {
            return Result.fail(OAuthGithubCallbackError.MISSING_CODE);
        }

        Result<GithubExchangeCodeResult, ?> exchangeResult = githubExchangeCodePort.exchange(code);

        if (!exchangeResult.success()) {
            return Result.fail(OAuthGithubCallbackError.CODE_EXCHANGE_FAILED);
        }

        GithubExchangeCodeResult tokenResult = exchangeResult.data();

        Result<GithubFetchUserInfoResult, ?> userInfoResult =
                githubFetchUserInfoPort.fetch(tokenResult.accessToken());

        if (!userInfoResult.success()) {
            return Result.fail(OAuthGithubCallbackError.USER_INFO_FETCH_FAILED);
        }

        GithubFetchUserInfoResult userInfo = userInfoResult.data();

        return Result.ok(new OAuthGithubCallbackResult(tokenResult, userInfo));
    }
}