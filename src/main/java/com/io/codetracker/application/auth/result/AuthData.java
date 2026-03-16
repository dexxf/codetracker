package com.io.codetracker.application.auth.result;

import com.io.codetracker.domain.auth.entity.Auth;

public record AuthData (String userId, String authId, String email){
    

    public static AuthData from(Auth auth) {
        return new AuthData(auth.getUserId(), auth.getAuthId(), auth.getEmail().getValue());
    }
}
