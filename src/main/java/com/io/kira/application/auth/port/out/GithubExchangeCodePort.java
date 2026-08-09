package com.io.kira.application.auth.port.out;

import com.io.kira.application.auth.error.GithubExchangeCodeError;
import com.io.kira.application.auth.result.GithubExchangeCodeResult;
import com.io.kira.common.result.Result;

public interface GithubExchangeCodePort {
    Result<GithubExchangeCodeResult, GithubExchangeCodeError> exchange(String code);
}
