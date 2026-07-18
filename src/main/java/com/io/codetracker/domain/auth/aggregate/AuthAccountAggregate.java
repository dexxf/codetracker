package com.io.codetracker.domain.auth.aggregate;

import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.entity.GithubAccount;

public record AuthAccountAggregate(Auth auth, GithubAccount githubAccount) {
}
