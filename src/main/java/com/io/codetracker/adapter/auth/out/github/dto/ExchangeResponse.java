package com.io.codetracker.adapter.auth.out.github.dto;


public record ExchangeResponse (boolean success, GithubTokenResult fetchResult, String message) {

    public static ExchangeResponse ok(GithubTokenResult fetchResult) {
        return new ExchangeResponse(true, fetchResult, "Successfully exchanged token.");
    }

        public static ExchangeResponse fail(String message) {
        return new ExchangeResponse(false, null, message);
    }

}