package com.io.kira.domain.auth.aggregate;

import com.io.kira.domain.auth.entity.Auth;
import com.io.kira.domain.auth.entity.GithubAccount;

public record AuthAccountAggregate(Auth auth, GithubAccount githubAccount) {
}
