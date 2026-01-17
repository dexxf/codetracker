package com.io.codetracker.application.user.dto;

import java.util.Map;

public record UserRegistrationDTO(boolean success, Map<String, Object> data, String message) {

    public static UserRegistrationDTO ok(Map<String, Object> data, String message){
        return new UserRegistrationDTO(true, data, message);
    }

    public static UserRegistrationDTO fail(String message){
        return new UserRegistrationDTO(false, null, message);
    }
}
