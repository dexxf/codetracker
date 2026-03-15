package com.io.codetracker.adapter.user.in.dto.response;

import com.io.codetracker.application.user.result.UserData;

public record UserInitResponse(UserData data, String message) {

    public static UserInitResponse ok(UserData data){
        return new UserInitResponse(data, "Successfully In");
    }

    public static UserInitResponse fail(String message){
        return new UserInitResponse(null, message);
    }
}
