package com.io.codetracker.application.auth.port.out;

import com.io.codetracker.application.auth.error.GithubExchangeCodeError;
import com.io.codetracker.application.auth.result.GithubExchangeCodeResult;
import com.io.codetracker.common.result.Result;

public interface GithubExchangeCodePort {
    Result<GithubExchangeCodeResult, GithubExchangeCodeError> exchange(String code);
}
