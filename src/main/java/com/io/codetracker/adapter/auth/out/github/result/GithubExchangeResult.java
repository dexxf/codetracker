package com.io.codetracker.adapter.auth.out.github.result;


public record GithubExchangeResult(boolean success, GithubTokenResult fetchResult, String message) {

    public static GithubExchangeResult ok(GithubTokenResult fetchResult) {
        return new GithubExchangeResult(true, fetchResult, "Successfully exchanged token.");
    }

    public static GithubExchangeResult fail(String message) {
        return new GithubExchangeResult(false, null, message);
    }

}