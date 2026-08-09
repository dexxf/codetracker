package com.io.kira.application.auth.port.out;

import com.io.kira.application.auth.error.GithubFetchUserInfoError;
import com.io.kira.application.auth.result.GithubFetchUserInfoResult;
import com.io.kira.common.result.Result;

public interface GithubFetchUserInfoPort {
    Result<GithubFetchUserInfoResult, GithubFetchUserInfoError> fetch(String accessToken);
}
