package com.io.codetracker.application.auth.port.out;

import com.io.codetracker.application.auth.error.GithubFetchUserInfoError;
import com.io.codetracker.application.auth.result.GithubFetchUserInfoResult;
import com.io.codetracker.common.result.Result;

public interface GithubFetchUserInfoPort {
    Result<GithubFetchUserInfoResult, GithubFetchUserInfoError> fetch(String accessToken);
}
