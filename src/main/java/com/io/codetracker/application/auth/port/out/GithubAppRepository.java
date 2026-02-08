package com.io.codetracker.application.auth.port.out;


import com.io.codetracker.domain.auth.entity.GithubAccount;

public interface GithubAppRepository {
    void save(GithubAccount githubAccount);
}
